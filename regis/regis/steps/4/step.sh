#!/bin/sh

BOLD='\033[0;1m'
DIM='\033[0;2m'
UNDERLINED='\033[0;4m'
BLINK='\033[0;5m'
REVERSE='\033[0;7m'
HIDDEN='\033[0;8m'

DEFAULT='\033[0;39m'
BLACK='\033[0;30m'
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
MAGENTA='\033[0;35m'
CYAN='\033[0;36m'
WHITE='\033[0;97m'
NC='\033[0m' # No Color


rm -rf ./assets/frames.json > /dev/null 2>&1

currentBranch=$(git branch --show-current)
if [ ${currentBranch} != "feat/drawing" ]; then
    printf "${RED}Merci de se positionner sur la branche 'feature/drawing' avant tout.${NC}\n"; 1>&2
    exit 5;
fi

nbCommitAhead=$(git rev-list --left-right --pretty=oneline ${currentBranch}...origin/${currentBranch} | wc -l)

if [ ${nbCommitAhead} -gt 0 ]; then
    printf "${RED}Vous n'avez pas push la branche ${currentBranch} sur le repertoire distant${NC}\n";  1>&2
    exit 5;
fi


cp -R $CWD/assets/frames.json assets/frames.json

git add assets/frames.json > /dev/null 2>&1
git commit -m"feat(drawing): add some stars" > /dev/null 2>&1
git push $ORIGIN ${currentBranch} --force > /dev/null 2>&1

