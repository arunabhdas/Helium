<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { DropdownMenu,
         DropdownMenuContent,
         DropdownMenuItem,
         DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { Icon } from '@iconify/vue'

const colorMode = useColorMode()

// Function to handle theme change
function setColorMode(theme: 'light' | 'dark' | 'system') {
  colorMode.preference = theme
}
</script>

<template>
  <div class="min-h-screen">
    <!-- Theme toggle dropdown in the top right corner -->
    <div class="fixed top-4 right-4 z-50">
      <DropdownMenu>
        <DropdownMenuTrigger as-child>
          <Button variant="outline" size="icon">
            <Icon v-if="colorMode.value === 'dark'" icon="radix-icons:sun" class="h-[1.2rem] w-[1.2rem]" />
            <Icon v-else icon="radix-icons:moon" class="h-[1.2rem] w-[1.2rem]" />
            <span class="sr-only">Toggle theme</span>
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end">
          <DropdownMenuItem @click="setColorMode('light')">
            <Icon icon="radix-icons:sun" class="mr-2 h-4 w-4" />
            <span>Light</span>
          </DropdownMenuItem>
          <DropdownMenuItem @click="setColorMode('dark')">
            <Icon icon="radix-icons:moon" class="mr-2 h-4 w-4" />
            <span>Dark</span>
          </DropdownMenuItem>
          <DropdownMenuItem @click="setColorMode('system')">
            <Icon icon="radix-icons:desktop" class="mr-2 h-4 w-4" />
            <span>System</span>
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    </div>
    
    <!-- Your app content goes here -->
    <div class="container mx-auto p-4">
      <h1 class="text-2xl font-bold mb-4">Helium App</h1>
      <p>Helium ERP</p>
    </div>
  </div>
</template>
