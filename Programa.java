public class Programa {
    public static void main(String[] args) {
        GerenciadorEstoque estoque = new GerenciadorEstoque();

        System.out.println("1. TESTANDO CADASTRO DE PRODUTOS:");
        try {
            estoque.adicionarProduto(new Produto(101, "Arroz 5kg", 28.50, 40));
            estoque.adicionarProduto(new Produto(102, "Feijão 1kg", 8.90, 65));
            estoque.adicionarProduto(new Produto(103, "Óleo de Soja", 6.50, 30));
        } catch (CadastroInvalidoException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n2. TESTANDO ERRO DE CÓDIGO DUPLICADO:");
        try {
            estoque.adicionarProduto(new Produto(101, "Macarrão", 4.50, 20));
        } catch (CadastroInvalidoException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n3. TESTANDO ERRO DE PREÇO INVÁLIDO:");
        try {
            estoque.adicionarProduto(new Produto(104, "Leite Integral", -5.00, 50));
        } catch (CadastroInvalidoException e) {
            System.out.println(e.getMessage());
        }

        estoque.listarProdutos();

        System.out.println("4. TESTANDO BUSCA DE PRODUTO EXISTENTE E INEXISTENTE:");
        try {
            Produto encontrado = estoque.buscarPorCodigo(102);
            System.out.printf("Produto localizado: %s (R$ %.2f)\n", encontrado.getNome(), encontrado.getPreco());

            estoque.buscarPorCodigo(999);
        } catch (ProdutoNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n5. TESTANDO REMOÇÃO E CONTINUIDADE DO PROGRAMA:");
        try {
            estoque.removerProduto(103);
            estoque.removerProduto(555);
        } catch (ProdutoNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        estoque.listarProdutos();
    }
}