import java.util.Scanner;

public class Main {

    public static Scanner input = new Scanner(System.in);
    public static Scanner input_txt = new Scanner(System.in);
    public static String CABECALHO = "\n================== OPERADORA MÓVEL ==================\n";
    public static String resposta = "n";


    //matrizes e total de registos
    public static String[][] lista_clientes = new String[9999][4];//0-nome, 1-nif, 2-morada, 3-quantidade telemovéis
    public static int total_clientes = 0;
    public static String[][] lista_telemoveis = new String[9999][3];//0-modelo, 1-numero, 2-nif
    public static int total_telemoveis = 0;


    public static void main(String[] args) {

        System.out.println();

        int opcao;

        preRegistos();

        do {

            exibirMenu();

            System.out.print("Opção: ");
            opcao = input.nextInt();

            limpar();

            System.out.println(CABECALHO);

            switch (opcao){

                case 1: registarCliente(); break;
                case 2:
                    if(total_clientes > 0){editarCliente();}
                    else{informarErroSemClientes();}
                    break;
                case 3:
                    if(total_clientes > 0){apagarCliente();}
                    else{informarErroSemClientes();}
                    break;
                case 4:
                    if(total_clientes > 0){registarCliente();}
                    else{informarErroSemClientes();}
                    break;
                case 5:
                    if(total_telemoveis > 0){editarCliente();}
                    else{informarErroSemTelemoveis();}
                    break;
                case 6:
                    if(total_telemoveis > 0){apagarTelemovel();}
                    else{informarErroSemTelemoveis();}
                    break;
                case 7:
                    if(total_clientes > 0){exibirListaClientes();}
                    else{informarErroSemClientes();}
                    break;
                case 8:
                    if(total_telemoveis > 0){exibirListaTelemoveis();}
                    else{informarErroSemTelemoveis();}
                    break;
                case 9:
                    if(total_clientes > 0){procurarPorNif();}
                    else{informarErroSemClientes();}
                    break;
                case 0: sair(); break;
                default: informarErro(); break;
            }

            if(opcao != 0){

                clicarEnter();
            }

            limpar();

            aguardar(3000);


        }while (!resposta.equalsIgnoreCase("s"));

    }


    public static void registarCliente(){

        String nome_digitado, nif_digitado, morada_digitada;

        System.out.println("---------------- REGISTO DE CLIENTE ----------------\n");

        System.out.print("- Digite o NOME do cliente: ");
        nome_digitado = input_txt.nextLine();
        System.out.print("- Digite o NIF do cliente: ");
        nif_digitado = input_txt.nextLine();
        System.out.print("- Digite a MORADA do cliente: ");
        morada_digitada = input_txt.nextLine();

        if(nifExisteListaClientes(nif_digitado)){

            lista_clientes[total_clientes][0] = nome_digitado;
            lista_clientes[total_clientes][1] = nif_digitado;
            lista_clientes[total_clientes][2] = morada_digitada;
            lista_clientes[total_clientes][3] = "0";

            total_clientes++;

            System.out.println("\nSUCESSO! Cliente registado.");
        }
        else{
            System.out.println("\nERRO! NIF digitado já está assiciado a um cliente.");
        }
    }

    public static void editarCliente(){

        String nif_digitado, nome_novo, nif_novo, morada_nova;
        int posicao_cliente, quantidade_telemoveis;
        boolean cliente_encontrado = false;

        System.out.println("------------------ EDITAR CLIENTE ------------------\n");

        System.out.print("- Digite o NIF do cliente: ");
        nif_digitado =  input_txt.nextLine();

        for(int i=0;  i<total_clientes; i++){

            if(nif_digitado.equalsIgnoreCase(lista_clientes[i][1])){

                posicao_cliente = getPosicaoListaClientes(nif_digitado);

                System.out.print("\n- Digite o NOME novo do cliente: ");
                nome_novo = input_txt.nextLine();
                System.out.print("- Digite o NIF novo do cliente: ");
                nif_novo = input_txt.nextLine();
                System.out.print("- Digite a MORADA nova do cliente: ");
                morada_nova = input_txt.nextLine();

                if(nifExisteListaClientes(nif_novo) || nif_novo.equalsIgnoreCase(lista_clientes[posicao_cliente][1])){

                    lista_clientes[posicao_cliente][0] = nome_novo;
                    lista_clientes[posicao_cliente][1] = nif_novo;
                    lista_clientes[posicao_cliente][2] = morada_nova;

                    quantidade_telemoveis = Integer.parseInt(lista_clientes[posicao_cliente][3]);

                    if(quantidade_telemoveis > 0){

                        for(int j=0; j<total_telemoveis; j++){

                            if (nif_digitado.equalsIgnoreCase(lista_telemoveis[j][2])){

                                lista_telemoveis[j][2] = lista_clientes[posicao_cliente][1];
                                quantidade_telemoveis--;
                            }
                        }
                    }

                    System.out.println("\nSUCESSO! Cliente editado.");
                }
                else{
                    System.out.println("\nERRO! NIF já associado a outro cliente.");
                }

                cliente_encontrado = true;
                break;
            }
        }

        if(!cliente_encontrado){
            System.out.println("\nERRO! NIF não associado a nenhum cliente.");
        }

    }

    public static void apagarCliente(){

        String nif_digitado;
        boolean cliente_encontrado = false;
        int posicao_cliente, quantidade_telemoveis, posicao_telemovel;

        System.out.println("------------------ APAGAR CLIENTE ------------------\n");

        System.out.print("- Digite o NIF do cliente: ");
        nif_digitado =  input_txt.nextLine();

        for(int i=0; i<total_clientes; i++){

            if(nif_digitado.equalsIgnoreCase(lista_clientes[i][1])){

                posicao_cliente = getPosicaoListaClientes(nif_digitado);
                quantidade_telemoveis = Integer.parseInt(lista_clientes[posicao_cliente][3]);

                if(quantidade_telemoveis > 0){

                    while(quantidade_telemoveis >0){

                        for (int j=0; j<total_telemoveis; j++){

                            if (nif_digitado.equalsIgnoreCase(lista_telemoveis[j][2])){

                                posicao_telemovel = getPosicaoListaTelemoveis(lista_clientes[posicao_cliente][1]);

                                total_telemoveis--;
                                quantidade_telemoveis--;

                                for(int k=j; k<total_telemoveis; k++){

                                    lista_telemoveis[k][0] = lista_telemoveis[k+1][0];
                                    lista_telemoveis[k][1] = lista_telemoveis[k+1][1];
                                    lista_telemoveis[k][2] = lista_telemoveis[k+1][2];

                                }
                            }
                        }

                    }
                }

                total_clientes--;

                for(int j=i; j<total_clientes; j++){

                    lista_clientes[j][0] = lista_clientes[j+1][0];
                    lista_clientes[j][1] = lista_clientes[j+1][1];
                    lista_clientes[j][2] = lista_clientes[j+1][2];
                    lista_clientes[j][3] = lista_clientes[j+1][3];

                }

                System.out.println("\nSUCESSO! Cliente apagado.");

                cliente_encontrado = true;
                break;
            }
        }

        if(!cliente_encontrado){
            System.out.println("\nERRO! NIF não associado a nenhum cliente.");
        }
    }


    public static void registarTelemovel(){

        String modelo_digitado, numero_digitado, nif_digitado;
        int posicao_cliente, quantidade_telemoveis;

        System.out.println("--------------- REGISTO DE TELEMÓVEL ---------------\n");

        System.out.print("- Digite o MODELO do telemóvel: ");
        modelo_digitado = input_txt.nextLine();
        System.out.print("- Digite o NÚMERO do telemóvel: ");
        numero_digitado = input_txt.nextLine();
        System.out.print("- Digite o NIF do cliente: ");
        nif_digitado = input_txt.nextLine();

        if(numeroExisteListaTelemoveis(numero_digitado)){

            if(!nifExisteListaClientes(nif_digitado)){

                posicao_cliente = getPosicaoListaClientes(nif_digitado);
                quantidade_telemoveis = Integer.parseInt(lista_clientes[posicao_cliente][3]);
                quantidade_telemoveis++;
                lista_clientes[posicao_cliente][3] = Integer.toString(quantidade_telemoveis);

                lista_telemoveis[total_telemoveis][0] = modelo_digitado;
                lista_telemoveis[total_telemoveis][1] = numero_digitado;
                lista_telemoveis[total_telemoveis][2] = lista_clientes[posicao_cliente][1];

                total_telemoveis++;

                System.out.println("\nSUCESSO! Telemóvel registado.");
            }
            else{
                System.out.println("\nERRO! NIF não associado a nenhum cliente.");
            }
        }
        else{
            System.out.println("\nERRO! Número de telemóvel já existe.");
        }
    }
    public static void editarTelemovel(){

        String numero_digitado, modelo_novo, numero_novo, nif_novo;
        int posicao_telemovel, posicao_cliente, quantidade_telemoveis, posicao_cliente_antigo, quantidade_telemoveis_antigos;

        System.out.println("----------------- EDITAR TELEMÓVEL -----------------\n");

        System.out.print("- Digite o número de telemóvel a editar: ");
        numero_digitado = input_txt.nextLine();

        if(!numeroExisteListaTelemoveis(numero_digitado)){

            posicao_telemovel = getPosicaoListaTelemoveisNumero(numero_digitado);

            System.out.print("\n- Digite o MODELO novo do telemóvel: ");
            modelo_novo = input_txt.nextLine();
            System.out.print("- Digite o NÚMERO novo do telemóvel: ");
            numero_novo = input_txt.nextLine();
            System.out.print("- Digite o NIF novo do cliente: ");
            nif_novo = input_txt.nextLine();


            if(numeroExisteListaTelemoveis(numero_novo) || numero_novo.equalsIgnoreCase(lista_telemoveis[posicao_telemovel][1])){

                if(!nifExisteListaClientes(nif_novo)){

                    if(!nif_novo.equalsIgnoreCase(lista_telemoveis[posicao_telemovel][2])){

                        posicao_cliente = getPosicaoListaClientes(nif_novo);
                        quantidade_telemoveis = Integer.parseInt(lista_clientes[posicao_cliente][3]);
                        quantidade_telemoveis++;
                        lista_clientes[posicao_cliente][3] = Integer.toString(quantidade_telemoveis);


                        posicao_cliente_antigo = getPosicaoListaClientes(lista_telemoveis[posicao_telemovel][2]);
                        quantidade_telemoveis_antigos = Integer.parseInt(lista_clientes[posicao_cliente_antigo][3]);
                        quantidade_telemoveis_antigos--;
                        lista_clientes[posicao_cliente_antigo][3] = Integer.toString(quantidade_telemoveis_antigos);
                    }

                    lista_telemoveis[posicao_telemovel][0] = modelo_novo;
                    lista_telemoveis[posicao_telemovel][1] = numero_novo;
                    lista_telemoveis[posicao_telemovel][2] = nif_novo;

                    System.out.println("\nSUCESSO! Telemóvel editado.");
                }
                else{
                    System.out.println("\nERRO! NIF não associado a nenhum cliente.");
                }
            }
            else{
                System.out.println("\nERRO! Número de telemóvel já registado.");
            }
        }
        else{
            System.out.println("\nERRO! Número de telemóvel não registado.");
        }

    }
    public static void apagarTelemovel(){

        String numero_digitado, nif_cliente;
        int posicao_telemovel, posicao_cliente, quantidade_telemoveis;

        System.out.println("----------------- APAGAR TELEMÓVEL -----------------\n");

        System.out.print("- Digite o número de telemóvel a apagar: ");
        numero_digitado = input_txt.nextLine();

        if(!numeroExisteListaTelemoveis(numero_digitado)){

            posicao_telemovel = getPosicaoListaTelemoveisNumero(numero_digitado);

            nif_cliente = lista_telemoveis[posicao_telemovel][2];

            posicao_cliente = getPosicaoListaClientes(nif_cliente);

            quantidade_telemoveis = Integer.parseInt(lista_clientes[posicao_cliente][3]);
            quantidade_telemoveis--;
            lista_clientes[posicao_cliente][3] = Integer.toString(quantidade_telemoveis);

            total_telemoveis--;

            for(int i=posicao_telemovel;i<total_telemoveis; i++){

                lista_telemoveis[i][0] = lista_telemoveis[i+1][0];
                lista_telemoveis[i][1] = lista_telemoveis[i+1][1];
                lista_telemoveis[i][2] = lista_telemoveis[i+1][2];
            }

            System.out.println("\nSUCESSO! Número de telemóvel apagado.");
        }
        else{
            System.out.println("\nERRO! Número de telemóvel não existe.");
        }
    }
    public static void procurarPorNif(){

        String nif_digitado;
        boolean cliente_encontrado = false, telemovel_encontrado = false;

        System.out.println("------------ BUSCA DE TELEMÓVEIS POR NIF -----------\n");

        System.out.print("- Digite o NIF do cliente que deseja consultar: ");
        nif_digitado = input_txt.nextLine();

        for(int i=0; i<total_clientes; i++){

            if(nif_digitado.equalsIgnoreCase(lista_clientes[i][1])){

                cliente_encontrado = true;

                System.out.print("\nCliente ("+ i +") - NOME: " + lista_clientes[i][0] + " | NIF: ");
                System.out.print(lista_clientes[(i)][1] + " | MORADA: " + lista_clientes[i][2]);
                System.out.println(" | QUANTIDADE DE TELEMÓVEIS: " + lista_clientes[i][3]);

                for(int j=0;j<total_telemoveis; j++){

                    telemovel_encontrado = true;
                    System.out.println();

                    if(nif_digitado.equalsIgnoreCase(lista_telemoveis[j][2])){

                        System.out.print("- Telemóvel ("+ (j) + ") - NÚMERO: ");
                        System.out.println(lista_telemoveis[j][1] + " | MODELO: " + lista_telemoveis[j][0]);
                    }

                }


                if(!telemovel_encontrado){

                    System.out.println("\nCliente sem telemóveis registados!");
                }
            }
        }

        if(!cliente_encontrado){
            System.out.println("\nERRO, NIF não pertence a nenhum cliente.");
        }

    }

    public static void exibirListaClientes(){

        String tecla = "";
        int total_paginas;
        int x = 1, clientes_restantes = total_clientes;

        if(total_clientes % 5 == 0){
            total_paginas = (total_clientes / 5);

        }
        else{
            total_paginas = (total_clientes / 5) +  1;
        }

        for (int i=0; i<total_clientes; i+=5){

            System.out.println("---------------- LISTA DE CLIENTES -----------------\n");

            System.out.println("*** Pagina " + x + "/" + total_paginas + " ***\n");


            for(int j=0; j<5; j++){

                if(clientes_restantes>0){

                    System.out.print("Cliente ("+(j+i)+") - NOME: " + lista_clientes[(j+i)][0] + " | NIF: ");
                    System.out.print(lista_clientes[(j+i)][1] + " | MORADA: " + lista_clientes[(j+i)][2]);
                    System.out.println(" | QUANTIDADE DE TELEMÓVEIS: " + lista_clientes[(j+i)][3]);
                    clientes_restantes--;
                }

            }

            x++;

            System.out.println("\n\nPrima a tecla <ENTER> para continuar...");
            System.out.println("Prima a tecla <X> para encerrar...");
            tecla = input_txt.nextLine();

            if(tecla.equalsIgnoreCase("x")){
                break;
            }

            limpar();

        }

    }

    public static void exibirListaTelemoveis(){

        String tecla = "";
        int total_paginas;
        int x = 1, telemoveis_restantes = total_telemoveis;

        System.out.println("****** LISTA DE TELEMÓVEIS ******\n");

        if(total_telemoveis % 5 == 0){
            total_paginas = (total_telemoveis / 5);

        }
        else{
            total_paginas = (total_telemoveis / 5) +  1;
        }

        for (int i=0; i<total_telemoveis; i+=5){

            System.out.println("---------------- LISTA DE TELEMÓVEIS ---------------\n");

            System.out.println("*** Pagina " + x + "/" + total_paginas + " ***\n");

            for(int j=0; j<5; j++){

                if(telemoveis_restantes>0){

                    int posicao = getPosicaoListaClientes(lista_telemoveis[j+i][2]);
                    String nome = lista_clientes[posicao][0];

                    System.out.print("- Telemóvel ("+ (j+i) + ") - CLIENTE: " + nome + " | NÚMERO: ");
                    System.out.println(lista_telemoveis[j+i][1] + " | MODELO: " + lista_telemoveis[j+i][0]);

                    telemoveis_restantes--;
                }

            }

            x++;

            System.out.println("\n\nPrima a tecla <ENTER> para continuar...");
            System.out.println("Prima a tecla <X> para encerrar...");
            tecla = input_txt.nextLine();

            if(tecla.equalsIgnoreCase("x")){
                break;
            }

            limpar();

        }

    }



    public static boolean nifExisteListaClientes(String nif){

        for(int i=0; i<total_clientes; i++){

            if(nif.equalsIgnoreCase(lista_clientes[i][1])){
                return false;
            }
        }
        return true;
    }

    public static boolean numeroExisteListaTelemoveis(String numero){

        for(int i=0; i<total_telemoveis; i++){
            if(numero.equalsIgnoreCase(lista_telemoveis[i][1])){
                return false;
            }
        }
        return true;
    }

    public static int getPosicaoListaClientes(String nif){

        for(int i=0; i<total_clientes; i++){

            if(nif.equalsIgnoreCase(lista_clientes[i][1])){
                return i;
            }
        }
        return -1;
    }

    public static int getPosicaoListaTelemoveis(String nif){

        for(int i=0; i<total_telemoveis; i++){

            if(nif.equalsIgnoreCase(lista_telemoveis[i][2])){
                return i;
            }
        }
        return -1;
    }

    public static int getPosicaoListaTelemoveisNumero(String numero){

        for(int i=0; i<total_telemoveis; i++){

            if(numero.equalsIgnoreCase(lista_telemoveis[i][1])){
                return i;
            }
        }
        return -1;
    }




    public static void exibirMenu(){

        System.out.println(CABECALHO);

        System.out.println("----------------------- MENU -----------------------\n");

        System.out.println("1 - Registar Cliente.");
        System.out.println("2 - Editar Cliente.");
        System.out.println("3 - Apagar Cliente.\n");

        System.out.println("4 - Registar Telemóvel.");
        System.out.println("5 - Editar Telemóvel.");
        System.out.println("6 - Apagar Telemovel.\n");

        System.out.println("7 - Listar Clientes.");
        System.out.println("8 - Listar Telemóveis.\n");

        System.out.println("9 - Buscar por cliente especifíco.\n");

        System.out.println("0 - Sair.\n");
    }

    public static void preRegistos(){

        //CLIENTES
        lista_clientes[0] = new String[] {"Ana", "111", "Lisboa", "1"};
        lista_clientes[1] = new String[] {"Pedro", "222", "Porto", "2"};
        lista_clientes[2] = new String[] {"Rita", "333", "Braga", "1"};
        lista_clientes[3] = new String[] {"Tatiana", "444", "Santarém", "3"};
        lista_clientes[4] = new String[] {"Bruno", "555", "Faro", "1"};
        lista_clientes[5] = new String[] {"André", "666", "Évora", "2"};
        lista_clientes[6] = new String[] {"Margarida", "777", "Leiria", "1"};

        total_clientes += 7;

        //TELEMÓVEIS
        lista_telemoveis[0] = new String[] {"Iphone", "9111", "111"};
        lista_telemoveis[1] = new String[] {"Samsung", "9222", "222"};
        lista_telemoveis[2] = new String[] {"Xiaomi", "9333", "333"};
        lista_telemoveis[3] = new String[] {"Huawei", "9444", "444"};
        lista_telemoveis[4] = new String[] {"Alcatel", "9555", "555"};
        lista_telemoveis[5] = new String[] {"LG", "9666", "666"};
        lista_telemoveis[6] = new String[] {"Motorola", "9777", "777"};

        lista_telemoveis[7] = new String[] {"Xiaomi", "9292", "222"};
        lista_telemoveis[8] = new String[] {"Samsung", "9494", "444"};
        lista_telemoveis[9] = new String[] {"ASUS", "9696", "666"};
        lista_telemoveis[10] = new String[] {"Iphone", "9499", "444"};

        total_telemoveis += 11;
    }

    public static void clicarEnter(){

        String enter;

        System.out.print("\nPrima a tecla <ENTER> para continuar...");
        enter = input_txt.nextLine();
    }

    public static void limpar(){

        for(int i=0; i<25; i++){
            System.out.println();
        }
    }

    public static void aguardar(int ms){

        try{
            Thread.sleep(ms);
        }catch (InterruptedException erro){
            erro.printStackTrace();
        }
    }

    public static void informarErroSemClientes(){

        System.out.println("ERRO! Não tem nenhum cliente registado!");
    }

    public static void informarErroSemTelemoveis(){

        System.out.println("ERRO! Não tem nenhum telemóvel registado!");
    }

    public static void sair(){

        System.out.print("Tem certeza que quer sair? (s/n) ");
        resposta = input_txt.nextLine();

        if(resposta.equalsIgnoreCase("s")){
            System.out.println("\nA SAIR...");
        }
    }
    public static void informarErro(){
        System.out.println("OPÇÃO INVÁLIDA!");
    }
}