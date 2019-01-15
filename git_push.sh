#!/bin/sh
#git config --global user.name kiran20v
#git config --global user.email kiran20v@gmail.com
git init Grails3_Ang6_QuartzUI
git add *
echo "Commit message:"
read commit_msg
git commit -m "$commit_msg"
git remote add python_projects https://github.com/kiran20v/Grails3_Ang6_QuartzUI
git push -u Grails3_Ang6_QuartzUI --all
#git show
echo "Pushed changes to Github"