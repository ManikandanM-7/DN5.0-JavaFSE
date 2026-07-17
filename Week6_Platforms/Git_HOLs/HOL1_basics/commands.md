# HOL 1 - Git Basics

## what i did

### step 1 - set up git identity
```bash
git config --global user.name "Manikandan M"
git config --global user.email "manikandan.m2027@mku.edu.in"
git config --list
```

### step 2 - create and init repo
```bash
mkdir my-first-repo
cd my-first-repo
git init
```

### step 3 - track a file
```bash
echo "hello git" > readme.txt
git status
git add readme.txt
git commit -m "first commit"
```

### step 4 - make changes and commit
```bash
echo "added this line" >> readme.txt
git diff
git add .
git commit -m "updated readme"
```

### step 5 - view history
```bash
git log
git log --oneline
git log --oneline --graph --all
```

### step 6 - undo things
```bash
# undo last commit, keep files staged
git reset --soft HEAD~1

# undo last commit, keep files unstaged
git reset --mixed HEAD~1

# undo everything - careful with this one
git reset --hard HEAD~1

# restore a single file to last commit state
git checkout -- readme.txt
```

### step 7 - .gitignore
```bash
# created .gitignore to ignore compiled files
echo "target/" >> .gitignore
echo "*.class" >> .gitignore
echo ".idea/" >> .gitignore
git add .gitignore
git commit -m "add gitignore"
```

## commands summary
| command | what it does |
|---------|-------------|
| git init | start tracking a folder |
| git add | stage files for commit |
| git commit | save a snapshot |
| git status | see whats changed |
| git log | see commit history |
| git diff | see line by line changes |
| git reset | undo commits |
