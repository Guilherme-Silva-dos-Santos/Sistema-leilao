import java.io.File;
import java.util.ArrayList;

public class ProdutoDAOTest {
    public static void main(String[] args) {
        File dbFile = new File("db/leiloes.db");
        if (dbFile.exists()) {
            dbFile.delete();
        }

        ProdutosDAO dao = new ProdutosDAO();

        ProdutosDTO produto = new ProdutosDTO();
        produto.setNome("Notebook Gamer");
        produto.setValor(2500);
        produto.setStatus("A Venda");

        dao.cadastrarProduto(produto);

        ArrayList<ProdutosDTO> produtos = dao.listarProdutos();
        if (produtos.size() != 1) {
            throw new RuntimeException("Teste falhou: a listagem não retornou 1 produto cadastrado.");
        }

        ProdutosDTO salvo = produtos.get(0);
        if (!"Notebook Gamer".equals(salvo.getNome()) || !"A Venda".equals(salvo.getStatus())) {
            throw new RuntimeException("Teste falhou: os dados do produto não foram persistidos corretamente.");
        }

        dao.venderProduto(salvo.getId());
        ArrayList<ProdutosDTO> vendidos = dao.listarProdutosVendidos();
        if (vendidos.size() != 1) {
            throw new RuntimeException("Teste falhou: o produto vendido não foi listado como vendido.");
        }

        ProdutosDTO vendido = vendidos.get(0);
        if (!"Vendido".equals(vendido.getStatus())) {
            throw new RuntimeException("Teste falhou: o status do produto não foi atualizado para Vendido.");
        }

        System.out.println("TESTE_OK -> produtos persistidos e vendidos: " + vendidos.size());
    }
}
