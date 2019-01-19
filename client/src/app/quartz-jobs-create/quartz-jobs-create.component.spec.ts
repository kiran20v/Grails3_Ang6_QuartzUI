import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuartzJobsCreateComponent } from './quartz-jobs-create.component';

describe('QuartzJobsCreateComponent', () => {
  let component: QuartzJobsCreateComponent;
  let fixture: ComponentFixture<QuartzJobsCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuartzJobsCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuartzJobsCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
