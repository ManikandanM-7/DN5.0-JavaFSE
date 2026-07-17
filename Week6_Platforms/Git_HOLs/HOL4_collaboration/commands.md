# HOL 4 - Collaboration with Fork and Pull Requests

## what i did

### forking workflow
```bash
# 1. fork the repo on github (click fork button)
# 2. clone your fork
git clone https://github.com/ManikandanM-7/forked-repo.git
cd forked-repo

# 3. add original repo as upstream
git remote add upstream https://github.com/original-owner/repo.git
git remote -v
# shows both origin (your fork) and upstream (original)
```

### making changes and creating pull request
```bash
# always create a new branch for your changes
git checkout -b fix-login-bug

# make your changes
echo "fixed the null pointer in login" >> LoginService.java
git add .
git commit -m "fix null pointer in login service"

# push to YOUR fork
git push origin fix-login-bug

# then go to github and create PR from fix-login-bug to upstream main
```

### keeping fork up to date with upstream
```bash
git fetch upstream
git checkout main
git merge upstream/main
git push origin main
```

### what happens in a PR review
- reviewer leaves comments on specific lines
- you make changes, push more commits to same branch
- PR automatically updates
- reviewer approves and merges

### git stash - useful when switching branches mid-work
```bash
# save uncommitted work temporarily
git stash

# switch branch, do stuff
git checkout main
git pull

# come back and restore
git checkout fix-login-bug
git stash pop
```
