# 커밋 메시지 브랜치명 넣을까?
define ENV_VAR
source_up
export GIT_BRANCH=`git rev-parse --abbrev-ref HEAD`
endef
export ENV_VAR

init:
	@command git config core.hooksPath githooks
	@command find .git/hooks -type l -exec rm {} \;
	@command find githooks -type f -exec ln -sf ../../{} .git/hooks/ \;

	@echo "$$ENV_VAR" > .envrc
	direnv allow
