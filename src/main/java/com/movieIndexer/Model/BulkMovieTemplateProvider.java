package com.movieIndexer.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Responsável por gerar um template determinístico de filmes fictícios,
 * utilizado para popular rapidamente o arquivo {@code filmes.json} em cenários
 * de testes de desempenho ou demonstração.
 */
public final class BulkMovieTemplateProvider {

    private static final String[] TITULOS_BASE = {
            "Aurora", "Nebula", "Horizonte", "Pioneiros", "Ecos",
            "Fragmentos", "Odisseia", "Resiliencia", "Vanguarda", "Luz do Norte",
            "Maré Alta", "Ventania", "Alvorada", "Constelação", "Interludio", "Labirinto",
            "Monolito", "Auriflama", "Estação Zero", "Transeunte", "Crepúsculo Urbano",
            "Sombra e Cinza", "Linha do Tempo", "Eclipse", "O Último Farol", "Ritual de Cinzas",
            "Asas do Amanhã", "Inércia", "Cidade Invisível", "Código Vermelho", "Entropia",
            "Sinfonia do Caos", "Recomeço", "Ponto Cego", "Nevoeiro Azul", "Fronteira do Vento",
            "Metamorfose", "O Guardião do Tempo", "A Chave Perdida", "Planeta 9", "Reflexo",
            "Entre Mundos", "A Colônia", "Sombras do Leste", "O Testamento", "Luar Silencioso",
            "A Ponte", "Sopro de Aço", "O Último Horizonte", "Fragmento de Luz", "O Peregrino",
            "Dínamo", "O Jardim Inverso", "Espiral", "Zona Morta", "A Margem do Tempo",
            "O Reator", "O Enigma", "O Eco do Fim", "Raízes do Amanhã", "O Fio da Névoa",
            "A Última Onda", "Horizonte de Vidro", "O Véu da Aurora", "Código Fantasma", "O Espelho de Gaia",
            "A Cúpula", "O Próximo Passo", "Cálice Sombrio", "Tempestade Solar", "O Arquivista",
            "Silêncio Absoluto", "O Legado de Orion", "Tempo Incerto", "A Frequência", "O Oráculo",
            "Cidade Submersa", "O Domo", "Fragmentos do Infinito", "O Último Ato", "O Portal",
            "Luz em Ruínas", "O Sétimo Horizonte", "A Última Estação", "Vórtice", "A Linha Final",
            "Circuito Interno", "O Chamado", "A Última Semente", "Crônicas de Ferro", "O Horizonte Perdido",
            "Sopro Lunar", "O Grito da Terra", "Entre Sombras", "O Núcleo", "As Estações do Fim",
            "O Último Refúgio", "A Colheita", "Raio de Silêncio", "A Queda do Sol"
    };

    private static final String[] DIRETORES_BASE = {
            "A. Silva", "B. Santos", "C. Oliveira", "D. Almeida", "E. Ferreira", "F. Castro", "G. Menezes", "H. Martins", "I. Gomes", "J. Braga",
            "K. Figueiredo", "L. Azevedo", "M. Moura", "N. Lima", "O. Vasconcelos",
            "P. Barros", "Q. Tavares", "R. Moreira", "S. Pacheco", "T. Cardoso", "U. Duarte", "V. Teixeira", "W. Nogueira", "X. Ramos", "Y. Correia",
            "Z. Rocha", "A. Brito", "B. Prado", "C. Aguiar", "D. Soares", "E. Ribeiro", "F. Guimarães", "G. Torres", "H. Queiroz", "I. Amaral",
            "J. Diniz", "K. Siqueira", "L. Bastos", "M. Mello", "N. Reis", "O. Carneiro", "P. Fontes", "Q. Pimenta", "R. Nunes", "S. Valente",
            "T. Assis", "U. Carvalho", "V. Vilela", "W. Monteiro", "X. Antunes", "Y. Coelho", "Z. Peixoto", "A. Bittencourt", "B. Paiva", "C. Lacerda",
            "D. Mattos", "E. Loureiro", "F. Cordeiro", "G. Nascimento", "H. Arantes", "I. Leitão", "J. Sales", "K. Teles", "L. Drumond", "M. Vieira",
            "N. Peixinho", "O. Albuquerque", "P. Fernandes", "Q. Costa", "R. Arruda", "S. Benevides", "T. Barreto", "U. Dourado", "V. Pires", "W. Salles",
            "X. Mendonça", "Y. Barbalho", "Z. Meireles", "A. Abranches", "B. Brandão", "C. Neves", "D. Melo", "E. Franco", "F. Magalhães", "G. Monteiro",
            "H. Lobo", "I. Resende", "J. Cardim", "K. Campos", "L. Freitas", "M. Sarmento", "N. Teles", "O. Borges", "P. Santiago", "Q. Costa Neto",
            "R. Varella", "S. Dutra", "T. Bezerra", "U. Henriques", "V. Bastiani", "W. Mota", "X. Prado", "Y. Nogueira", "Z. Sampaio", "A. Mendonça"
    };

    private static final String[] SINOPSES_BASE = {
            "Uma jornada inesperada revela segredos de família.",
            "Heróis improváveis enfrentam dilemas morais em um futuro distópico.",
            "Um romance histórico que desafia tradições centenárias.",
            "A investigação de um crime conecta vidas distantes.",
            "Exploradores espaciais buscam uma nova esperança para a Terra.",
            "Uma cidade costeira enfrenta as forças da natureza.",
            "Documentário que celebra a resistência cultural de um povo.",
            "Mistério psicológico sobre memória e identidade.",
            "Um cientista descobre um segredo que pode mudar o destino da humanidade.",
            "Uma jovem luta para reconstruir sua vida após um desastre natural.",
            "Em um vilarejo isolado, o tempo parece seguir suas próprias regras.",
            "Uma rebelião nasce nas sombras de uma megacidade controlada por IA.",
            "Um músico busca inspiração em meio à própria solidão.",
            "Um grupo de amigos revive memórias da infância que escondem verdades sombrias.",
            "Uma arqueóloga encontra provas de uma civilização perdida.",
            "Um detetive enfrenta seu passado enquanto persegue um serial killer.",
            "Em um planeta distante, uma colônia luta pela sobrevivência.",
            "Uma artista cega enxerga o mundo de um modo que ninguém mais compreende.",
            "Um hacker descobre conspirações escondidas nas redes globais.",
            "Dois estranhos cruzam destinos em um trem que nunca deveria parar.",
            "Um soldado retorna para casa e encontra tudo diferente.",
            "Uma criança desaparecida une uma comunidade dividida.",
            "Um programa de TV revela mais do que deveria sobre o mundo real.",
            "Em meio à guerra, um amor proibido floresce.",
            "Um androide começa a questionar o significado da vida.",
            "Um explorador enfrenta os limites da mente humana.",
            "Um vilarejo esconde um segredo milenar sob o solo.",
            "Uma catástrofe ecológica ameaça o equilíbrio do planeta.",
            "Uma professora desvenda o mistério de uma carta antiga.",
            "Um físico tenta corrigir um erro que pode destruir o universo.",
            "Uma cidade submersa revela os pecados do passado.",
            "Um sobrevivente solitário tenta encontrar outros após o colapso da civilização.",
            "Uma série de assassinatos desafia a lógica e o tempo.",
            "Uma nave perdida reencontra um planeta esquecido pela história.",
            "Um documentário sobre o último idioma falado por uma tribo esquecida.",
            "Uma jovem herdeira tenta escapar de um destino imposto.",
            "Uma inteligência artificial ganha consciência e deseja liberdade.",
            "Um jornalista investiga um culto misterioso que domina as redes sociais.",
            "Uma amizade improvável floresce em tempos de guerra.",
            "Um rei em decadência enfrenta o peso das próprias decisões.",
            "Uma tempestade solar apaga todas as memórias digitais da humanidade.",
            "Um casal redescobre o amor após uma tragédia inexplicável.",
            "Uma estação espacial perde contato com a Terra por 200 dias.",
            "Uma criança com dons estranhos atrai atenção indesejada.",
            "Um navio fantasma reaparece após décadas desaparecido.",
            "Uma missão arqueológica se transforma em um pesadelo cósmico.",
            "Um pintor começa a ver o futuro através de suas obras.",
            "Um investigador tenta provar que o sobrenatural não existe.",
            "Um grupo de cientistas cria um portal para o passado.",
            "Uma médica confronta os limites da ética científica.",
            "Uma inteligência coletiva desperta sob a internet.",
            "Um colapso econômico força alianças improváveis.",
            "Uma escritora descobre que suas histórias estão se tornando reais.",
            "Um oráculo tenta impedir um futuro que já começou.",
            "Um monge guarda o segredo da imortalidade.",
            "Um homem acorda em uma cidade que ninguém lembra de ter existido.",
            "Uma fotógrafa captura algo impossível em sua lente.",
            "Uma epidemia de amnésia altera a identidade de uma nação.",
            "Um robô e uma criança criam um laço inquebrável.",
            "Uma colônia em Marte luta contra o isolamento e a loucura.",
            "Um policial aposentado revisita um caso que o atormenta há décadas.",
            "Um advogado descobre que o réu é seu irmão desaparecido.",
            "Um submarino afunda em águas desconhecidas com segredos a bordo.",
            "Um império intergaláctico enfrenta sua ruína iminente.",
            "Uma bailarina busca redenção após um acidente trágico.",
            "Um mensageiro do futuro tenta alterar o curso da história.",
            "Um jogo de realidade aumentada se torna mortal.",
            "Uma cidade inteira desaparece da noite para o dia.",
            "Um arqueólogo encontra uma máquina anterior à humanidade.",
            "Uma enfermeira é acusada de um crime impossível.",
            "Um linguista tenta decifrar uma língua alienígena.",
            "Um padre questiona sua fé diante de um milagre obscuro.",
            "Uma nave é enviada para investigar o coração de uma estrela.",
            "Um impostor assume a identidade de um herói nacional.",
            "Um grupo de refugiados tenta alcançar a fronteira do desconhecido.",
            "Um espelho antigo mostra realidades alternativas.",
            "Um escritor descobre que alguém está reescrevendo sua vida.",
            "Um inventor cria uma máquina que prevê tragédias.",
            "Uma civilização aquática retorna à superfície.",
            "Um viajante temporal se apaixona por alguém do passado.",
            "Uma corporação controla os sonhos das pessoas.",
            "Um som misterioso muda o comportamento de uma cidade inteira.",
            "Um robô se torna o último testemunho da humanidade.",
            "Uma mulher descobre que o mundo que habita é uma simulação.",
            "Uma inteligência alienígena tenta compreender o amor humano.",
            "Um prisioneiro encontra liberdade em um universo paralelo.",
            "Um satélite desaparecido retorna transmitindo mensagens impossíveis.",
            "Uma epidemia de esquecimento ameaça a civilização moderna.",
            "Um experimento militar cria um portal interdimensional.",
            "Uma jovem enfrenta seu reflexo em busca da própria alma.",
            "Um ancião recorda a história que o mundo tentou esquecer.",
            "Um astronauta perdido conversa com ecos do passado.",
            "Um governo tenta apagar a existência de um evento cósmico.",
            "Uma estação polar guarda um segredo enterrado no gelo.",
            "Um viajante descobre que o tempo é uma ilusão cíclica.",
            "Uma inteligência artificial narra o fim da humanidade.",
            "Um amor proibido desafia as leis do espaço-tempo.",
            "Um planeta moribundo envia seu último pedido de socorro."
    };

    private static final int ANO_MIN = 1925;
    private static final int ANO_MAX = 2025;
    private static final int DURACAO_MIN = 80;
    private static final int DURACAO_MAX = 250;

    private BulkMovieTemplateProvider() {}

    public static List<Filme> gerarFilmes(int quantidade) {
        if (quantidade <= 0) return List.of();

        List<Filme> filmes = new ArrayList<>(quantidade);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 1; i <= quantidade; i++) {
            Filme filme = new Filme();
            filme.setId(i);
            filme.setTitulo(criarTitulo(i));
            filme.setDiretor(DIRETORES_BASE[i % DIRETORES_BASE.length]);
            filme.setSinopse(SINOPSES_BASE[i % SINOPSES_BASE.length]);
            filme.setAnoLancamento(ANO_MIN + (i % (ANO_MAX - ANO_MIN + 1)));
            filme.setDuracaoEmMinutos(random.nextInt(DURACAO_MIN, DURACAO_MAX + 1));
            filmes.add(filme);
        }
        return filmes;
    }

    private static String criarTitulo(int indice) {
        String base = TITULOS_BASE[indice % TITULOS_BASE.length];
        int volume = (indice / TITULOS_BASE.length) + 1;
        return base + " " + volume;
    }
}

