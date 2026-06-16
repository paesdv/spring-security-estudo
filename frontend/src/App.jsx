import Header from './components/Header'
import Footer from './components/Footer'
import Home from './pages/Home'
import SecondPage from './pages/SecondPage.jsx'
import ThirdPage from './pages/ThirdPage.jsx'
import {BrowserRouter, Route, Routes} from "react-router-dom";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/pagina2" element={<SecondPage />} />
                <Route path="/pagina3" element={<ThirdPage />} />
            </Routes>
        </BrowserRouter>

    )
}

export default App