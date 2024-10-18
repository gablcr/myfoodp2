package br.ufal.ic.p2.myfood.gerenciador;

import br.ufal.ic.p2.myfood.banco.Dados;
import br.ufal.ic.p2.myfood.exception.*;
import br.ufal.ic.p2.myfood.exception.UsuarioNaoEhEntregadorException;
import br.ufal.ic.p2.myfood.exception.usuario.*;
import br.ufal.ic.p2.myfood.models.Dono;
import br.ufal.ic.p2.myfood.models.Entregador;
import br.ufal.ic.p2.myfood.models.Usuario;
import br.ufal.ic.p2.myfood.models.Cliente;

import java.util.Map;

public class GerenteUsuario {
     private final Map<Integer, Usuario> listaUsuario;

    private static final String DONO = "dono";
    private static final String ENTREGADOR = "entregador";

    public GerenteUsuario(Dados dados) {
        this.listaUsuario = dados.getListaUsuario();
    }

    public String getUsuarioAtributo(int userId, String atributo) throws Exception {
        Usuario usuario = listaUsuario.get(userId);
        if (usuario == null) throw new UsuarioNaoCadastradoException();
        String n_atributo = atributo;

        return switch (n_atributo) {
            case "nome" -> usuario.getNome();
            case "senha" -> usuario.getSenha();
            case "email" -> usuario.getEmail();
            case "endereco" -> usuario.getEndereco();
            case "cpf" -> getCpf(usuario);
            case "veiculo" -> getVeiculo(usuario);
            case "placa" -> getPlaca(usuario);
            default -> throw new AtributoInvalidoException();
        };
    }

    public void criarCliente(String nome, String email, String senha, String endereco) throws Exception {
        validaUsuario(nome, email, senha, endereco);

        Usuario usuario = new Cliente(nome, email, senha, endereco);
        listaUsuario.put(usuario.getId(), usuario);
    }

    public void criarDono(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        if (cpf == null || cpf.length() != 14) throw new CpfInvalidoException();

        validaUsuario(nome, email, senha, endereco);

        Usuario user = new Dono(nome, email, senha, endereco, cpf);
        listaUsuario.put(user.getId(), user);
    }

    public void criarEntregador(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        if (veiculo == null || veiculo.isEmpty()) throw new VeiculoInvalidoException();
        if (placa == null || placa.isEmpty()) throw new PlacaInvalidaException();

        for (Usuario usuario : listaUsuario.values()) {
            if (usuario.tipoUsuario().equals(ENTREGADOR)) {
                Entregador entregador = (Entregador) usuario;
                if (entregador.getPlaca().equals(placa)) throw new PlacaInvalidaException();
            }
        }

        validaUsuario(nome, email, senha, endereco);

        Usuario usuario = new Entregador(nome, email, senha, endereco, veiculo, placa);
        listaUsuario.put(usuario.getId(), usuario);
    }

    public int signIn(String email, String senha) throws Exception {
        for (Usuario usuario : listaUsuario.values()) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha))
                return usuario.getId();
        }
        throw new LoginOuSenhaInvalidosException();
    }

    public String getEmpresas(int idEntregador) throws Exception {
        Entregador usuario = listaUsuario.values().stream()
                .filter(u -> u.tipoUsuario().equals(ENTREGADOR))
                .map(u -> (Entregador) u)
                .filter(u -> u.getId() == idEntregador)
                .findFirst()
                .orElseThrow(UsuarioNaoEhEntregadorException::new) ;

        return usuario.empresasEmString();
    }

    private void validaUsuario(String nome, String email, String senha, String endereco) throws Exception {
        if (nome == null || nome.isEmpty()) throw new NomeInvalidoException();
        if (email == null || !email.contains("@")) throw new EmailInvalidoException();
        if (senha == null || senha.isEmpty()) throw new SenhaInvalidaException();
        if (endereco == null || endereco.isEmpty()) throw new EnderecoInvalidoException();
        if (listaUsuario.values().stream().anyMatch(usuario -> usuario.getEmail().equals(email)))
            throw new ContaComEmailJaExisteException();
    }

    private String getCpf(Usuario usuario) throws AtributoInvalidoException {
        if (usuario.tipoUsuario().equals(DONO)) return ((Dono) usuario).getCpf();
        else throw new AtributoInvalidoException();
    }

    private String getVeiculo(Usuario usuario) throws AtributoInvalidoException {
        if (usuario.tipoUsuario().equals(ENTREGADOR)) return ((Entregador) usuario).getVeiculo();
        else throw new AtributoInvalidoException();
    }

    private String getPlaca(Usuario usuario) throws AtributoInvalidoException {
        if (usuario.tipoUsuario().equals(ENTREGADOR)) return ((Entregador) usuario).getPlaca();
        else throw new AtributoInvalidoException();
    }
    
}
