function Home() {
    const produtos = [
        { id: 1, nome: 'Camiseta', preco: 49.90 },
        { id: 2, nome: 'Tênis', preco: 199.90 },
        { id: 3, nome: 'Boné', preco: 79.90 },
    ]

    return (
        <div>
            <h2 className="text-2xl font-bold mb-6">Produtos em Destaque</h2>

            <div className="grid grid-cols-3 gap-6">
                {produtos.map(p => (
                    <div key={p.id} className="bg-white rounded shadow p-4">
                        <h3 className="text-lg font-semibold">{p.nome}</h3>
                        <p className="text-gray-500">R$ {p.preco.toFixed(2)}</p>
                        <button className="mt-4 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 w-full">
                            Adicionar ao Carrinho
                        </button>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default Home