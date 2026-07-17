# HOL 5 - Gitflow Workflow

## gitflow branch structure

```
main        -> production ready code only
develop     -> integration branch, all features merge here
feature/*   -> new features branched from develop
release/*   -> prep for release, branched from develop
hotfix/*    -> urgent fixes on main
```

## what i did

### setup
```bash
# main and develop are the long-lived branches
git checkout -b develop main
git push origin develop
```

### starting a new feature
```bash
# branch off develop
git checkout -b feature/user-registration develop

# work on the feature
git add .
git commit -m "add user registration form"
git commit -m "add email validation"

# merge back into develop when done
git checkout develop
git merge --no-ff feature/user-registration
git commit -m "merge feature/user-registration into develop"

# delete feature branch
git branch -d feature/user-registration
git push origin develop
```

### creating a release
```bash
# when develop has enough features for a release
git checkout -b release/1.0 develop

# only bug fixes on release branch
git commit -m "fix typo in error message"
git commit -m "bump version to 1.0"

# merge into both main and develop
git checkout main
git merge --no-ff release/1.0
git tag -a v1.0 -m "version 1.0"

git checkout develop
git merge --no-ff release/1.0

git branch -d release/1.0
```

### hotfix on production
```bash
# urgent fix needed on main
git checkout -b hotfix/fix-login-crash main

git commit -m "fix app crash on login with special chars"

# merge into main AND develop
git checkout main
git merge --no-ff hotfix/fix-login-crash
git tag -a v1.0.1 -m "hotfix 1.0.1"

git checkout develop
git merge --no-ff hotfix/fix-login-crash

git branch -d hotfix/fix-login-crash
```

## why gitflow
- keeps main always deployable
- features are isolated
- easy to track what went into each release
- hotfixes dont disrupt ongoing feature work
