/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.awt.GraphicsEnvironment;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    private void mostrarMensagem(String mensagem) {
        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null, mensagem);
        }
    }
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void criarTabelaSeNaoExistir() {
        String sql = "CREATE TABLE IF NOT EXISTS produtos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "valor INTEGER NOT NULL,"
                + "status TEXT NOT NULL"
                + ");";

        try (Connection conn = new conectaDAO().connectDB();
             PreparedStatement prep = conn.prepareStatement(sql)) {
            prep.executeUpdate();
        } catch (SQLException erro) {
            mostrarMensagem("Erro ao criar tabela: " + erro.getMessage());
        }
    }
    
    public void cadastrarProduto(ProdutosDTO produto) {
        criarTabelaSeNaoExistir();

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try (Connection conn = new conectaDAO().connectDB();
             PreparedStatement prep = conn.prepareStatement(sql)) {
            if (conn == null) {
                mostrarMensagem("Não foi possível conectar ao banco de dados.");
                return;
            }

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            int linhasAfetadas = prep.executeUpdate();

            if (linhasAfetadas > 0) {
                mostrarMensagem("Cadastro realizado com sucesso!");
            } else {
                mostrarMensagem("Cadastro não foi realizado.");
            }
        } catch (SQLException erro) {
            mostrarMensagem("Erro ao cadastrar produto: " + erro.getMessage());
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        criarTabelaSeNaoExistir();

        ArrayList<ProdutosDTO> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY id";

        try (Connection conn = new conectaDAO().connectDB();
             PreparedStatement prep = conn.prepareStatement(sql);
             ResultSet resultset = prep.executeQuery()) {

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                produtos.add(produto);
            }
        } catch (SQLException erro) {
            mostrarMensagem("Erro ao listar produtos: " + erro.getMessage());
        }

        return produtos;
    }

    public void venderProduto(Integer idProduto) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try (Connection conn = new conectaDAO().connectDB();
             PreparedStatement prep = conn.prepareStatement(sql)) {
            prep.setInt(1, idProduto);
            int linhasAfetadas = prep.executeUpdate();

            if (linhasAfetadas > 0) {
                mostrarMensagem("Produto vendido com sucesso!");
            } else {
                mostrarMensagem("Produto não encontrado para venda.");
            }
        } catch (SQLException erro) {
            mostrarMensagem("Erro ao vender produto: " + erro.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        criarTabelaSeNaoExistir();

        ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido' ORDER BY id";

        try (Connection conn = new conectaDAO().connectDB();
             PreparedStatement prep = conn.prepareStatement(sql);
             ResultSet resultset = prep.executeQuery()) {

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                produtosVendidos.add(produto);
            }
        } catch (SQLException erro) {
            mostrarMensagem("Erro ao listar produtos vendidos: " + erro.getMessage());
        }

        return produtosVendidos;
    }
}

