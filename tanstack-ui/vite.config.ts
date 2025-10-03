import react from '@vitejs/plugin-react'
import { tanstackRouter } from '@tanstack/router-plugin/vite'
import { defineConfig } from 'vite'
import tsConfigPaths from 'vite-tsconfig-paths'

export default defineConfig({
  server: {
    port: 4206,
  },
  base: '/tanstack-ui',
  ssr: {
    noExternal: ['@mui/*'],
  },
  plugins: [tanstackRouter({
    target: 'react',
      autoCodeSplitting: true,
  }), 
    tsConfigPaths({
      projects: ['./tsconfig.json'],
    }),
    react()
  ],
})