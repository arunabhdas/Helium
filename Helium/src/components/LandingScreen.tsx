// src/components/LandingScreen.tsx
import React from 'react';
import { Button } from "@/components/ui/button";

export default function LandingScreen() {
  return (
    <div className="flex flex-col items-center justify-center min-h-svh">
      <h1 className="text-4xl font-bold mb-6">Welcome to the Landing Screen</h1>
      <p className="text-xl mb-8">This is your new landing page</p>
      <Button>Explore More</Button>
    </div>
  );
}