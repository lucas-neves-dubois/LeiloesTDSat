
import com.mysql.cj.Query;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;


public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        conn = new conectaDAO().connectDB();

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto Cadastrado com sucesso!"); // Feedback
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + ex.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        conn = new conectaDAO().connectDB();  // Conecta ao banco de dados
        String sql = "SELECT * FROM produtos";  // Query para listar todos os produtos

        ArrayList<ProdutosDTO> listagem = new ArrayList<>();  // Lista para armazenar os produtos

        try {
            prep = conn.prepareStatement(sql);  // Prepara a consulta
            resultset = prep.executeQuery();  // Executa e obtém os resultados

            while (resultset.next()) {  // Itera sobre os resultados
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));  // Supondo que há uma coluna 'id'
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);  // Adiciona o produto à lista
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar produtos: " + ex.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listagem;  // Retorna a lista preenchida
    }

    public void venderProduto(int idProduto) {
        conn = new conectaDAO().connectDB(); // Conecta ao banco de dados

        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?"; // Atualiza o status para "Vendido"

        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, idProduto); // Define o ID do produto

            int linhasAfetadas = prep.executeUpdate(); // Executa a atualização

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao vender produto: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + ex.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}