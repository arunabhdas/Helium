# Helium (Nuxt)


## Steps
* Initialize Nuxt project 
```
npx nuxi@latest init helium
```

```
─❯ npx nuxi@latest init helium
Need to install the following packages:
nuxi@3.22.5
Ok to proceed? (y) y


        .d$b.
       i$$A$$L  .d$b
     .$$F` `$$L.$$A$$.
    j$$'    `4$$:` `$$.
   j$$'     .4$:    `$$.
  j$$`     .$$:      `4$L
 :$$:____.d$$:  _____.:$$:
 `4$$$$$$$$P` .i$$$$$$$$P`

ℹ Welcome to Nuxt!                                                                         nuxi  3:52:56 AM
ℹ Creating a new project in helium.                                                        nuxi  3:52:56 AM

✔ Which package manager would you like to use?
npm
◐ Installing dependencies...                                                                nuxi  3:53:09 AM

> postinstall
> nuxt prepare

✔ Types generated in .nuxt                                                                 nuxi  3:54:00 AM

added 629 packages, and audited 631 packages in 50s

139 packages are looking for funding
  run `npm fund` for details

found 0 vulnerabilities
✔ Installation completed.                                                                  nuxi  3:54:00 AM

✔ Initialize git repository?
No
                                                                                            nuxi  3:54:59 AM
✨ Nuxt project has been created with the v3 template. Next steps:
 › cd helium                                      
```

* Add Tailwind and its configuration

```
npm install --save-dev @nuxtjs/tailwindcss
```