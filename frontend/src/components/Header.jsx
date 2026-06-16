function Header() {
    return (
        <header className="bg-blue-600 text-white p-4 flex justify-between items-center">
            <h1 className="text-xl font-bold">🛒 Meu Ecommerce</h1>
            <nav className="flex gap-6">
                <a href="#" className="hover:underline">Produtos</a>
                <a href="#" className="hover:underline">Carrinho</a>
            </nav>
        </header>
    )
}

export default Header