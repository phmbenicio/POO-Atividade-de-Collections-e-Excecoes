import java.util.ArrayList;

public class GerenciadorEstoque {
    private ArrayList<Produto> listaProdutos;

    public GerenciadorEstoque() {
        this.listaProdutos = new ArrayList<Produto>();
    }

    public void adicionarProduto(Produto novoProduto) throws CadastroInvalidoException {
        if (novoProduto.getNome() == null || novoProduto.getNome().isEmpty()) {
            throw new CadastroInvalidoException("Erro: O nome do produto não pode ser vazio.");
        }
        if (novoProduto.getPreco() <= 0) {
            throw new CadastroInvalidoException("Erro: O preço do produto deve ser maior que zero.");
        }
        if (novoProduto.getQuantidadeEstoque() < 0) {
            throw new CadastroInvalidoException("Erro: A quantidade em estoque não pode ser negativa.");
        }

        for (int i = 0; i < listaProdutos.size(); i++) {
            Produto p = listaProdutos.get(i);
            if (p.getCodigo() == novoProduto.getCodigo()) {
                throw new CadastroInvalidoException("Erro: Já existe um produto cadastrado com o código " + novoProduto.getCodigo() + ".");
            }
        }

        listaProdutos.add(novoProduto);
        System.out.println("Produto '" + novoProduto.getNome() + "' adicionado com sucesso!");
    }

    public void listarProdutos() {
        System.out.println("\n--- LISTA DE PRODUTOS NO ESTOQUE ---");
        if (listaProdutos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado no momento.");
        } else {
            for (int i = 0; i < listaProdutos.size(); i++) {
                Produto p = listaProdutos.get(i);
                System.out.printf("Código: %d | Nome: %s | Preço: R$ %.2f | Estoque: %d un.\n",
                        p.getCodigo(), p.getNome(), p.getPreco(), p.getQuantidadeEstoque());
            }
        }
        System.out.println("------------------------------------\n");
    }

    public Produto buscarPorCodigo(int codigo) throws ProdutoNaoEncontradoException {
        for (int i = 0; i < listaProdutos.size(); i++) {
            Produto p = listaProdutos.get(i);
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        throw new ProdutoNaoEncontradoException("Erro: Produto com o código " + codigo + " não foi encontrado no estoque.");
    }

    public void removerProduto(int codigo) throws ProdutoNaoEncontradoException {
        Produto produtoParaRemover = buscarPorCodigo(codigo);
        listaProdutos.remove(produtoParaRemover);
        System.out.println("Produto '" + produtoParaRemover.getNome() + "' removido com sucesso!");
    }
}