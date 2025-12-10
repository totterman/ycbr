import react from '@vitejs/plugin-react'
import { tanstackRouter } from '@tanstack/router-plugin/vite'
import { defineConfig } from 'vitest/config'
import tsConfigPaths from 'vite-tsconfig-paths'
import { intlayer } from "vite-intlayer"

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
    react(),
    intlayer()
  ],
  test: {
    globals: true,
    environment: 'jsdom',
//    setupFiles: './vitest.setup.ts',
    },
})