import os
import csv

# Função para ler os arquivos .txt e adicionar as 5 primeiras informações como colunas no CSV
def agrupar_txt_para_csv(pasta, arquivo_csv):
    # Abre o arquivo CSV para escrita
    with open(arquivo_csv, mode='w', newline='', encoding='utf-8') as csvfile:
        writer = csv.writer(csvfile)

        arquivos_processados = 0  # Contador para verificar quantos arquivos foram processados
        linhas_processadas = set()  # Conjunto para armazenar as linhas únicas (sem repetições)

        # Itera sobre todos os arquivos da pasta
        for nome_arquivo in os.listdir(pasta):
            # Verifica se é um arquivo .txt
            caminho_arquivo = os.path.join(pasta, nome_arquivo)
            if os.path.isfile(caminho_arquivo) and nome_arquivo.endswith('.txt'):
                print(f"Lendo o arquivo: {nome_arquivo}")  # Mensagem de depuração

                # Lê o conteúdo do arquivo
                with open(caminho_arquivo, 'r', encoding='utf-8') as file:
                    linhas = file.readlines()

                # Processa cada linha do arquivo
                for linha in linhas:
                    # Divide a linha em palavras (separadas por tabulação ou espaços)
                    partes = linha.split()

                    if len(partes) >= 7:  # Garante que a linha tem pelo menos 5 colunas
                        # Extrai as 5 primeiras informações
                        palavras = tuple(partes[:7])  # Usamos 'tuple' para garantir que possamos armazenar no set
                        
                        if palavras not in linhas_processadas:  # Verifica se a linha já foi processada
                            writer.writerow(palavras)  # Escreve a linha no CSV
                            linhas_processadas.add(palavras)  # Marca a linha como processada
                            arquivos_processados += 1
                    else:
                        print(f"A linha não contém 5 partes: {linha}")  # Mensagem de depuração

        # Verifica se algum arquivo foi processado
        if arquivos_processados == 0:
            print("Nenhuma linha única foi processada e gravada no CSV.")
        else:
            print(f"{arquivos_processados} linhas únicas foram processadas e gravadas no CSV.")

# Exemplo de uso
pasta_dos_arquivos = r'C:\Users\bruno.souza\Downloads\ADS-Lab1\output-bench-memoria'  # Substitua com o caminho correto
arquivo_csv = 'dataset-memoria.csv'  # Nome do arquivo CSV que será gerado

agrupar_txt_para_csv(pasta_dos_arquivos, arquivo_csv)
