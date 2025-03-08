import { useState } from 'react'
import './App.css'
import { Button } from "@/components/ui/button"
import { ThemeProvider } from "@/components/theme-provider"
import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom"
import LandingScreen from '@/components/LandingScreen'

function Home() {
  const navigate = useNavigate();
  
  const handleClick = () => {
    navigate('/landing');
  };

  return (
    <div className='flex flex-col items-center justify-center min-h-svh'>
      <Button onClick={handleClick}>Click me</Button>
    </div>
  );
}
function App() {
  const [count, setCount] = useState(0)

  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/landing" element={<LandingScreen />} />
        </Routes>
      </Router>
      <div className='flex flex-col items-center justify-center min-h-svh'>
        <Button>Click me</Button>
      </div>
    </ThemeProvider>
  )
}

export default App
