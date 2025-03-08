// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  modules: [
    '@nuxtjs/tailwindcss', 
    '@nuxtjs/color-mode',
    'shadcn-nuxt'
  ],
  colorMode: {
    classSuffix: '',
    preference: 'system', // default value of $colorMode.preference
    fallback: 'dark', // fallback value if not system preference found
  },
  shadcn: {
     /**
     * Prefix for all the imported component
     */
     prefix: '',
     /**
     * Directory that the component lives in.
     * @default "./components/ui"
     */
    componentDir: './components/ui'
  },
  compatibilityDate: '2024-11-01',
  devtools: { enabled: true },
})