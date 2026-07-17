# HOL 3 - Remote Repositories

## what i did

### connect local repo to github
```bash
# add remote origin
git remote add origin https://github.com/ManikandanM-7/

# verify
git remote -v
```

### push to github
```bash
# first push - set upstream
git push -u origin main

# after that just
git push
```

### clone a repo
```bash
git clone https://github.com/ManikandanM-7/
cd 
```

### pull latest changes
```bash
# fetch + merge in one step
git pull origin main

# or do it separately
git fetch origin
git merge origin/main
```

### working with remote branches
```bash
# push a local branch to remote
git push origin feature-login

# track remote branch
git checkout -b feature-login origin/feature-login

# delete remote branch
git push origin --delete feature-login
```

### what i learned
- origin is just the default name for remote
- git fetch downloads changes but doesnt apply them
- git pull = git fetch + git merge
- always pull before push to avoid conflicts
