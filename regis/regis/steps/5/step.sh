#!/bin/sh

rm -rf ./main.go > /dev/null 2>&1
rm -rf ./assets/frames.json > /dev/null 2>&1

git checkout -b feature/animation > /dev/null 2>&1

git checkout -b feature/animation_frames > /dev/null 2>&1

cp $CWD/assets/frames_1.json assets/frames.json
git add main.go assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 1" > /dev/null 2>&1

cp $CWD/assets/frames_2.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 2" > /dev/null 2>&1

cp $CWD/assets/frames_3.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 3" > /dev/null 2>&1

cp $CWD/assets/frames_4.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 4" > /dev/null 2>&1

cp $CWD/assets/frames_5.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 5" > /dev/null 2>&1

cp $CWD/assets/frames_6.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 6" > /dev/null 2>&1

cp $CWD/assets/frames_7.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 7" > /dev/null 2>&1

cp $CWD/assets/frames_8.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 8" > /dev/null 2>&1

cp $CWD/assets/frames_9.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 9" > /dev/null 2>&1

cp $CWD/assets/frames_10.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 10" > /dev/null 2>&1

cp $CWD/assets/frames_11.json assets/frames.json
git add assets > /dev/null 2>&1
git commit --author="John Doe <john@doe.com>"  -m"feat(animation): frame 11" > /dev/null 2>&1

git push $ORIGIN feature/animation_frames --force > /dev/null 2>&1

git checkout feature/animation > /dev/null 2>&1

git checkout -b feature/animation_last_frames_and_loop > /dev/null 2>&1

cp $CWD/assets/frames_12.json assets/frames.json
cp $CWD/main-loop.go main.go
git add assets main.go > /dev/null 2>&1
git commit -m"feat(animation): frame 12 + infinite loop" > /dev/null 2>&1

cp $CWD/assets/frames_12.json assets/frames.json
cp $CWD/main-noloop.go main.go
git add assets main.go > /dev/null 2>&1
git commit -m"feat(animation): frame 12 - infinite loop" > /dev/null 2>&1

git push $ORIGIN feature/animation_last_frames_and_loop --force > /dev/null 2>&1


git checkout feature/animation > /dev/null 2>&1
git merge --no-ff --strategy-option theirs -m "Merge branch 'feature/animation_frames' into feature/animation"  feature/animation_frames > /dev/null 2>&1

git merge --no-ff --strategy-option theirs -m "Merge branch 'feature/animation_last_frames_and_loop' into feature/animation" feature/animation_last_frames_and_loop > /dev/null 2>&1
git checkout --theirs -- main.go assets/frames.json > /dev/null 2>&1
git add main.go assets/frames.json > /dev/null 2>&1
git commit -am "Merge branch 'feature/animation_last_frames_and_loop' into feature/animation" > /dev/null 2>&1

git push $ORIGIN feature/animation --force > /dev/null 2>&1
