*Nome: Pedro Henrique Machado Benício*
*Matrícula: 202600677*

# 1) Descrição da Entidade e Justificativa da Coleção Utilizada

Para esta etapa do gerenciador de estoque de supermercado, utilizei a classe `Produto` contendo os atributos `codigo`, `nome`, `preco` e `quantidadeEstoque`. Além dela, criei a classe `GerenciadorEstoque` para armazenar os produtos em memória e aplicar as regras de negócio de adição, listagem, busca por código e remoção.

A estrutura de dados escolhida do Java Collections Framework foi o **`ArrayList`**. 
Escolhi o `ArrayList` porque, diferente de um array comum (`[]`) que possui tamanho fixo, ele cresce e diminui dinamicamente conforme adicionamos ou removemos mercadorias do estoque. Além disso, ele mantém a ordem de inserção dos produtos para a geração de relatórios e permite percorrer facilmente os itens usando um laço de repetição `for` tradicional.

## Classe Produto (Produto.java)

```java
public class Produto {
    private int codigo;
    private String nome;
    private double preco;
    private int quantidadeEstoque;

    public Produto(int codigo, String nome, double preco, int quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
}
```

# 2) Tratamento de Exceções e Exceções Personalizadas

Para evitar que dados incorretos sejam registrados no estoque ou que o programa pare de funcionar inesperadamente, criei duas classes de exceção personalizadas que herdam de `Exception`:
1. **`CadastroInvalidoException`**: É lançada quando o usuário tenta cadastrar um produto com um código que já existe na lista, ou quando informa dados inválidos (como nome vazio, preço menor ou igual a zero, ou quantidade negativa).
2. **`ProdutoNaoEncontradoException`**: É lançada quando o usuário tenta buscar ou remover um produto informando um código que não está cadastrado no `ArrayList`.

Nos métodos da classe `GerenciadorEstoque`, utilizei `throw new` para disparar essas exceções e `throws` na assinatura dos métodos para exigir que a classe principal trate o erro com `try` e `catch`.

## Exceção Personalizada 01 (CadastroInvalidoException.java)

```java
public class CadastroInvalidoException extends Exception {
    public CadastroInvalidoException(String mensagem) {
        super(mensagem);
    }
}
```

## Exceção Personalizada 02 (ProdutoNaoEncontradoException.java)

```java
public class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
```

# 3) Gerenciamento de Objetos com Collections Framework

Na classe `GerenciadorEstoque`, implementei os quatro métodos pedidos: `adicionarProduto`, `listarProdutos`, `buscarPorCodigo` e `removerProduto`.

## Classe Gerenciadora (GerenciadorEstoque.java)

```java
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
```

# 4) Classe de Teste (Programa Principal)

Fiz uma classe `Programa` para testar todas as operações da coleção e simular os erros de cadastro duplicado, preço negativo e busca/remoção de código inexistente. Mesmo após cair nos blocos `catch`, o programa exibe a mensagem de erro amigável e continua executando normalmente até o final.

## Classe Programa (Programa.java)

```java
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
```

# 5) Instruções para Executar o Programa

1. Clone o repositório ou baixe os arquivos `.java` na mesma pasta.
2. Compile os arquivos pelo terminal com o comando:
   `javac *.java`
3. Execute a classe principal com o comando:
   `java Programa`

# Resultado da execução do Programa

```text
1. TESTANDO CADASTRO DE PRODUTOS:
Produto 'Arroz 5kg' adicionado com sucesso!
Produto 'Feijão 1kg' adicionado com sucesso!
Produto 'Óleo de Soja' adicionado com sucesso!

2. TESTANDO ERRO DE CÓDIGO DUPLICADO:
Erro: Já existe um produto cadastrado com o código 101.

3. TESTANDO ERRO DE PREÇO INVÁLIDO:
Erro: O preço do produto deve ser maior que zero.

--- LISTA DE PRODUTOS NO ESTOQUE ---
Código: 101 | Nome: Arroz 5kg | Preço: R$ 28,50 | Estoque: 40 un.
Código: 102 | Nome: Feijão 1kg | Preço: R$ 8,90 | Estoque: 65 un.
Código: 103 | Nome: Óleo de Soja | Preço: R$ 6,50 | Estoque: 30 un.
------------------------------------

4. TESTANDO BUSCA DE PRODUTO EXISTENTE E INEXISTENTE:
Produto localizado: Feijão 1kg (R$ 8,90)
Erro: Produto com o código 999 não foi encontrado no estoque.

5. TESTANDO REMOÇÃO E CONTINUIDADE DO PROGRAMA:
Produto 'Óleo de Soja' removido com sucesso!
Erro: Produto com o código 555 não foi encontrado no estoque.

--- LISTA DE PRODUTOS NO ESTOQUE ---
Código: 101 | Nome: Arroz 5kg | Preço: R$ 28,50 | Estoque: 40 un.
Código: 102 | Nome: Feijão 1kg | Preço: R$ 8,90 | Estoque: 65 un.
------------------------------------
```
# 6) Capturas de Tela
<img width="1063" height="865" alt="Captura de tela 2026-11-12 111611" src="https://github.com/user-attachments/assets/856cb172-077a-446e-9e16-0c2b5c4ba8cd" />
<img width="612" height="578" alt="Captura de tela 2026-11-12 111725" src="https://github.com/user-attachments/assets/4a945bc1-735d-40b9-85eb-8f3b2f3f2b7e" />

