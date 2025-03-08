# Helium
Helium is a personal finances financial tracker app for keeping track of your portfolio and finances


## Screnshots

![Screenshot 1](https://raw.githubusercontent.com/arunabhdas/Helium/refs/heads/main/screenshots/helium_screenshot_1.png)

## Steps

- Follow instructions at https://ui.shadcn.com/docs/installation/vite
  to bootstrap a new React Javascript project using Vite
```
npm create vite@latest
```

- 
```
╰─❯ npm create vite@latest

> npx
> create-vite

│
◇  Project name:
│  Helium
│
◇  Package name:
│  heliumapp
│
◇  Select a framework:
│  React
│
◇  Select a variant:
│  TypeScript
│
◇  Scaffolding project in /Users/coder/repos/arunabhdas/githubrepos/Helium/Helium...
│
└  Done. Now run:

  cd Helium
  npm install
  npm run dev
```

* Add Tailwind CSS

```
npm install tailwindcss @tailwindcss/vite
```

Replace everything in src/index.css with the following:

```
src/index.css
@import "tailwindcss";
```

Edit tsconfig.json file
The current version of Vite splits TypeScript configuration into three files, two of which need to be edited. Add the baseUrl and paths properties to the compilerOptions section of the tsconfig.json and tsconfig.app.json files:

```
{
  "files": [],
  "references": [
    {
      "path": "./tsconfig.app.json"
    },
    {
      "path": "./tsconfig.node.json"
    }
  ],
  "compilerOptions": {
    "baseUrl": ".",
    "paths": {
      "@/*": ["./src/*"]
    }
  }
}
```
Edit tsconfig.app.json file
Add the following code to the tsconfig.app.json file to resolve paths, for your IDE:

```
{
  "compilerOptions": {
    // ...
    "baseUrl": ".",
    "paths": {
      "@/*": [
        "./src/*"
      ]
    }
    // ...
  }
}
````
Update vite.config.ts
Add the following code to the vite.config.ts so your app can resolve paths without error:

```
import path from "path"
import tailwindcss from "@tailwindcss/vite"
import react from "@vitejs/plugin-react"
import { defineConfig } from "vite"
 
// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
})
```

* Run the CLI

```
npx shadcn@latest init
```

