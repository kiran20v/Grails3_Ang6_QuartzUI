package com.micro.controller;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import dev.samstevens.totp.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MfaSetupController {

    @GetMapping("/mfa/setup")
   //data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAV4AAAFeAQAAAADlUEq3AAAC9UlEQVR42u2bsZHrMAxEoXHg0CVcKSyNKk2lqASHDjSHIxagKEq078/85IJ1Ylt6SmAQWCxp0X9/bUKYMGHChP8T/ha8yqenSBa56yoybXfVJcvthZs3wkPYPun8LXZbJn091jTbo1927/WoCOEBXGJpty2kebInF496AlyiTvgjjOvJw21RF/laMqJO+BcYSYnbCHdZ3eVRuRH+CMfqlnIdhdFSdCtx1vl9KSDcekpN0fPbsAERPikZK4WWoiVTS2SfnrfvZA/hiHNZyIL1bD1lTeqxz7rdfckTHsG2kMt1T0qjLDdbYRQEn/AQht6zPmyyDxQeFSgZaEElPITterlgwS7PqItA8WYSPVpuhEdwEc7luouWZHG2imhR91HOWvXxRyGsx9X9UJfK/oyVyRDOPvDmpmQIH+EYNiZ4BaIxbFick4aBcGzHhM+FEV0ZilnNZrHxt3wTZGqeCI/hDQtZUB8B++hhg8h0UYyEu3bsnTdkn6doVTLQNZ2SIdzB7geUwrj4gLZI+FWQN0tWwkO4Cj1vLf5Mrq1lk5jhCA9g3fZxzdpxrY+Ye31qk64UEO7a8Rp2Hkqhlclpk91mSdorGcJHEQjtIlVGl9XtzVmjK/f6mXCXohbn1j6qmvai2ceZ8MkldmNFY7aNOC9S7WbCY7g5KuaENvW3b2RkwmMYK9gia8ObWpxLUqKZxEZG54sSvljK8FdsTjuIwKT+LZ92PQh3hTH2gLzDJM9bMcqUzKSER3DEWdxtEV/ksa0RXsHlRyF83LQFJTAJ2hQcTn03tRHu9iZqNtZvLWGhcgi/h8sKPmxkuC/qb74reSM8guO1mwRox5MLGndJOyVD+HrmJA7jwW2R8JnrxpASHsJeGHX3Cnzn0XdyPc6E38H1MB5W99ZMgiXH6lbCH+HdqHr65o/uh2jn66FHwqcjoNVfMUNUXMlg4B39KITbOUY0kzmOgMZhPJH7sBQQPveUNe0DL85KSTvBTXgA8w84hAkTJvz34B8kiyn8oANeJgAAAABJRU5ErkJggg==
    public String setupDevice() throws QrGenerationException {
        // Generate and store the secret
        String secret = new DefaultSecretGenerator().generate();

        log.info("Secret: " + secret);
        QrData data = new QrData.Builder()
                .label("sample@test.com")
                .secret(secret)
                .issuer("AppName")
                .build();

        log.info("Algorithm and digits: {}, {}", data.getAlgorithm(), data.getDigits());
        QrGenerator qrGenerator = new ZxingPngQrGenerator();

        // Generate the QR code image data as a base64 string which
        // can be used in an <img> tag:
        String qrCodeImage = Utils.getDataUriForImage(
                qrGenerator.generate(data),
                qrGenerator.getImageMimeType()
        );

        log.info("Image: " + qrCodeImage);
        return qrCodeImage;
    }


    @PostMapping("/mfa/verify")
    @ResponseBody
    public String verify(@RequestParam String code) {
        // secret is fetched from some storage
        String secret = "TKHHQHIQVAABXXM72HJGL6KIW5427FEB";

        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator(HashingAlgorithm.SHA1);

        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        if (verifier.isValidCode(secret, code)) {
            return "CORRECT CODE";
        }

        return "INCORRECT CODE";
    }
}
