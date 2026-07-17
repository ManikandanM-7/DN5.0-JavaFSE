# HOL 2 - Branching and Merging

## what i did

### create and switch branches
```bash
# see all branches
git branch

# create new branch
git branch feature-login

# switch to it
git checkout feature-login

# shortcut - create and switch in one command
git checkout -b feature-payment
```

### work on the branch
```bash
# made changes on feature-login branch
echo "login code here" > login.java
git add .
git commit -m "add login feature"

# make another commit
echo "added validation" >> login.java
git add .
git commit -m "add login validation"
```

### merge back to main
```bash
# switch back to main
git checkout main

# merge feature branch into main
git merge feature-login

# output shows fast-forward or merge commit
```

### merge conflict - what happened when i got one
```bash
# both main and feature-login edited the same line
git merge feature-login
# CONFLICT - git couldnt auto merge

# opened the file and saw this
# <<<<<<< HEAD
# this is main version
# =======
# this is feature-login version
# >>>>>>> feature-login

# manually edited to keep the right version
# then staged and committed to resolve
git add .
git commit -m "resolve merge conflict"
```

### delete branch after merge
```bash
git branch -d feature-login
```

### useful branch commands
```bash
# see all branches including remote
git branch -a

# see last commit on each branch
git branch -v

# rename a branch
git branch -m old-name new-name
```
