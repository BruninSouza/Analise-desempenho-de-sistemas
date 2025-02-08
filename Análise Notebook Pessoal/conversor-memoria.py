import os
import csv

def agrupar_txt_para_csv(pasta, arquivo_csv):
    
    diretorio_csv = os.path.dirname(arquivo_csv)
    if diretorio_csv and not os.path.exists(diretorio_csv):
        os.makedirs(diretorio_csv)

    with open(arquivo_csv, mode='w', newline='', encoding='utf-8') as csvfile:
        writer = csv.writer(csvfile)

        arquivos_processados = 0  # Contador
        linhas_processadas = set()

        for nome_arquivo in os.listdir(pasta):
            # Verifica se é um arquivo .txt
            caminho_arquivo = os.path.join(pasta, nome_arquivo)
            if os.path.isfile(caminho_arquivo) and nome_arquivo.endswith('.txt'):
                print(f"Lendo o arquivo: {nome_arquivo}")

                # Lê o conteúdo do arquivo
                with open(caminho_arquivo, 'r', encoding='utf-8') as file:
                    linhas = file.readlines()

                for linha in linhas:
                    # Divide a linha em palavras (separadas por tabulação ou espaços)
                    partes = linha.split()

                    if len(partes) >= 7:
                        # Extrai as 7 primeiras informações para criar os titulos das colunas
                        palavras = tuple(partes[:7])
                        
                        if palavras not in linhas_processadas:  # Verifica se a linha já foi processada
                            writer.writerow(palavras)  # Escreve a linha no CSV
                            linhas_processadas.add(palavras)  # Marca a linha como processada
                            arquivos_processados += 1
                    else:
                        print(f"A linha não contém 5 partes: {linha}")

        # Verifica quantos arquivos foram processados ou se não foram
        if arquivos_processados == 0:
            print("Nenhuma linha única foi processada e gravada no CSV.")
        else:
            print(f"{arquivos_processados} linhas únicas foram processadas e gravadas no CSV.")

# Pasta onde está os arquivos a serem lidos
pasta_dos_arquivos = r'C:\Users\bruns\Downloads\ADS-Lab1\output-bench-memoria'  # Substitua para o caminho em seu dispositivo

# Garante que o CSV será criado na mesma pasta do script
diretorio_atual = os.path.dirname(os.path.abspath(__file__))  # Diretório onde o script está localizado
arquivo_csv = os.path.join(diretorio_atual, 'dataset-memoria.csv')  # Nome do arquivo CSV que será gerado

agrupar_txt_para_csv(pasta_dos_arquivos, arquivo_csv)
