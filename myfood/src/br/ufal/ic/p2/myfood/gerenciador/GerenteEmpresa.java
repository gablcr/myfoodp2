package br.ufal.ic.p2.myfood.gerenciador;

import br.ufal.ic.p2.myfood.exception.empresa.*;
import br.ufal.ic.p2.myfood.exception.usuario.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.myfood.banco.Dados;
import br.ufal.ic.p2.myfood.exception.*;
import br.ufal.ic.p2.myfood.models.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class GerenteEmpresa {
    private final Map<Integer, Empresa> listaEmpresa;
    private final Map<Integer, Usuario> listaUsuario;

    public GerenteEmpresa(Dados dados) {
        this.listaEmpresa = dados.getListaEmpresa();
        this.listaUsuario = dados.getListaUsuario();
    }

    private static final String HORARIO = "^([0-9][0-9]):[0-9][0-9]$";
    private static final String INVALIDO = "invalido";
    private static final String FARMACIA = "farmacia";
    private static final String RESTAURANTE = "restaurante";
    private static final String MERCADO = "mercado";
    private static final String DONO = "dono";
    private static final String ENTREGADOR = "entregador";
    private static final String CLIENTE = "cliente";

    public int criarEmpresaRestaurante(String tipoEmpresa, int idDono, String nomeEmpresa, String endereco, String cozinho) throws Exception {
        if (cozinho == null || cozinho.isEmpty()) throw new TipoCozinhaInvalidoException();

        validaEmpresas(tipoEmpresa, idDono, nomeEmpresa, endereco);

        Restaurante restaurante = new Restaurante(RESTAURANTE, idDono, nomeEmpresa, endereco, cozinho);
        listaEmpresa.put(restaurante.getId(), restaurante);
        return restaurante.getId();
    }

    public int criarEmpresaMercado(String tipoEmpresa, int idDono, String nomeEmpresa, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        if (abre == null || fecha == null) throw new HorarioInvalidoException();
        if (tipoMercado == null || tipoMercado.isEmpty()) throw new TipoMercadoInvalidoException();
        if (!abre.matches(HORARIO) || !fecha.matches(HORARIO)) throw new FormatoDeHoraInvalidoException();
        if (validTime(fecha)) throw new HorarioInvalidoException();

        validaEmpresas(tipoEmpresa, idDono, nomeEmpresa, endereco);

        Mercado mercado = new Mercado(MERCADO, idDono, nomeEmpresa, endereco, abre, fecha, tipoMercado);
        listaEmpresa.put(mercado.getId(), mercado);
        return mercado.getId();
    }

    public int criarEmpresaFarmacia(String tipoEmpresa, int idDono, String nomeEmpresa, String endereco, Boolean abre24horas, Integer numeroFuncionarios) throws Exception {
        validaEmpresas(tipoEmpresa, idDono, nomeEmpresa, endereco);

        Farmacia farmacia = new Farmacia(FARMACIA, idDono, nomeEmpresa, endereco, abre24horas, numeroFuncionarios);
        listaEmpresa.put(farmacia.getId(), farmacia);
        return farmacia.getId();
    }

    public String empresasPorDono(int idDono) throws Exception {
        Usuario usuario = listaUsuario.get(idDono);
        if (usuario == null) throw new UsuarioNaoCadastradoException();
        if (!usuario.tipoUsuario().equals(DONO)) throw new UsuarioNaoPodeCriarEmpresaException();

        return listaEmpresa.entrySet().stream()
                .filter(entry -> entry.getValue().getIdDono() == idDono)
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> String.format("[%s, %s]", entry.getValue().getNome(), entry.getValue().getEndereco()))
                .collect(Collectors.joining(", ", "{[", "]}"));
    }


    public int getIdEmpresa(int idDono, String nomeEmpresa, int index) throws Exception {
        if (nomeEmpresa == null || nomeEmpresa.isEmpty()) throw new NomeInvalidoException();
        if (index < 0) throw new IndiceInvalidoException();

        Usuario usuario = listaUsuario.get(idDono);
        if (usuario == null) throw new UsuarioNaoCadastradoException();

        List<Empresa> donoEmpresas = listaEmpresa.entrySet().stream()
                .filter(entry -> entry.getValue().getIdDono() == idDono)
                .filter(entry -> entry.getValue().getNome().equals(nomeEmpresa))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();

        if (donoEmpresas.isEmpty()) throw new NomeEmpresaNaoExisteException();
        if (donoEmpresas.size() <= index) throw new IndiceMaiorQueEsperadoException();

        return donoEmpresas.get(index).getId();
    }

    public String getEmpresaAtributo(int idEmpresa, String atributo) throws Exception {
        if (atributo == null) throw new AtributoInvalidoException();

        Empresa empresa = listaEmpresa.get(idEmpresa);
        if (empresa == null) throw new EmpresaNaoCadastradaException();

        String n_atributo = atributo;
        return switch (n_atributo) {
            case "nome" -> empresa.getNome();
            case "dono" -> listaUsuario.get(empresa.getIdDono()).getNome();
            case "endereco" -> empresa.getEndereco();
            case "tipoCozinha" -> getCozinho(empresa);
            case "abre" -> getAbre(empresa);
            case "fecha" -> getFecha(empresa);
            case "tipoMercado" -> getTipoMercado(empresa);
            case "aberto24Horas" -> getAbre24Horas(empresa);
            case "numeroFuncionarios" -> getNumeroFuncionarios(empresa);
            default -> throw new AtributoInvalidoException();
        };
    }

    public void mudarHorasMercado(int idMercado, String abre, String fecha) throws Exception {
        Empresa empresa = listaEmpresa.get(idMercado);
        if (!empresa.getTipoEmpresa().equals(MERCADO)) throw new NaoEhMercadoValido();

        Mercado mercado = (Mercado) empresa;

        if (abre == null || fecha == null) throw new HorarioInvalidoException();
        if (!abre.matches(HORARIO) || !fecha.matches(HORARIO)) throw new FormatoDeHoraInvalidoException();
        if (validTime(fecha)) throw new HorarioInvalidoException();

        mercado.setAbre(abre);
        mercado.setFecha(fecha);
    }

    public void registrarEntregador(int idEmpresa, int idEntregador) throws Exception {
        Empresa empresa = listaEmpresa.get(idEmpresa);
        if (empresa == null) throw new EmpresaNaoCadastradaException();

        Entregador usuario = listaUsuario.values().stream()
                .filter(u -> u.tipoUsuario().equals(ENTREGADOR))
                .map(u -> (Entregador) u)
                .filter(u -> u.getId() == idEntregador)
                .findFirst()
                .orElseThrow(UsuarioNaoEhEntregadorException::new);

        empresa.addEntregador(usuario);
        usuario.addEmpresa(empresa);
    }

    public String getEntregadores(int idEmpresa) throws Exception {
        Empresa empresa = listaEmpresa.get(idEmpresa);
        if (empresa == null) throw new EmpresaNaoExiste();

        return empresa.EntregadoresEmString();
    }

    private void validaEmpresas(String tipoEmpresa, int idDono, String nomeEmpresa, String endereco) throws Exception {

        if(tipoEmpresa == null) throw new TipoEmpresaInvalidoException();
        
        if (tipoEmpresa.equals(INVALIDO) || (!tipoEmpresa.equals(RESTAURANTE) && !tipoEmpresa.equals(MERCADO) && !tipoEmpresa.equals(FARMACIA)))
            throw new TipoEmpresaInvalidoException();

        if (nomeEmpresa == null || nomeEmpresa.isEmpty()) throw new NomeInvalidoException();
        if (endereco == null || endereco.isEmpty()) throw new EnderecoEmpresaInvalidoException();

        for (Empresa empresa : listaEmpresa.values()) {
            if (empresa.getIdDono() != idDono && empresa.getNome().equalsIgnoreCase(nomeEmpresa))
                throw new EmpresaComNomeJaExisteException();
            if (empresa.getIdDono() == idDono && empresa.getEndereco().equals(endereco) && empresa.getNome().equals(nomeEmpresa))
                throw new EmpresaMesmoNomeELocalException();
        }

        Usuario usuario = listaUsuario.get(idDono);
        if (usuario == null) throw new UsuarioNaoCadastradoException();
        if (usuario.tipoUsuario().equals(CLIENTE)) throw new UsuarioNaoPodeCriarEmpresaException();
    }

    private static boolean validTime(String time) {
        String[] parts = time.split(":");
        if (parts.length != 2) return true;
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return (hours < 6 || hours > 23) || (minutes < 0 || minutes > 59);
    }

    private String getCozinho(Empresa empresa) throws AtributoInvalidoException {
        if (empresa.getTipoEmpresa().equals(RESTAURANTE)) return ((Restaurante) empresa).getCozinho();
        throw new AtributoInvalidoException();
    }

    private String getAbre(Empresa empresa) throws AtributoInvalidoException {
        if (empresa.getTipoEmpresa().equals(MERCADO)) return ((Mercado) empresa).getAbre();
        throw new AtributoInvalidoException();
    }

    private String getFecha(Empresa empresa) throws AtributoInvalidoException {
        if (empresa.getTipoEmpresa().equals(MERCADO)) return ((Mercado) empresa).getFecha();
        throw new AtributoInvalidoException();
    }

    private String getTipoMercado(Empresa empresa) throws AtributoInvalidoException {
        if (empresa.getTipoEmpresa().equals(MERCADO)) return ((Mercado) empresa).getTipoMercado();
        throw new AtributoInvalidoException();
    }

    private String getAbre24Horas(Empresa empresa) throws AtributoInvalidoException {
        if (empresa.getTipoEmpresa().equals(FARMACIA)) return ((Farmacia) empresa).getAbre24Horas().toString();
        throw new AtributoInvalidoException();
    }

    private String getNumeroFuncionarios(Empresa empresa) throws AtributoInvalidoException {
        if (empresa.getTipoEmpresa().equals(FARMACIA)) return ((Farmacia) empresa).getNumeroFuncionarios().toString();
        throw new AtributoInvalidoException();
    }
    
}
