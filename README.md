Chamadas API PostMan e produtos Exemplo:

Método POST:
Chamada:
http://localhost:8080/products
Body Raw:
{
    "name": "Lavadora LG",
    "value": 5000.00
}

Método GET(GetAll):
Chamada:
http://localhost:8080/products
Body none

Método GET (GetOneProduct):
Chamada:
http://localhost:8080/products/*Informe o Id do Produto que deseja buscar.*
Body none

Método PUT (UpdateProduct):
Chamada:
http://localhost:8080/products/*Informe o Id do Produto que deseja atualizar.*
Body Raw:
{
    "name": "Lavadora LG 2500 Top - 2024",
    "value": 9000.00
}

Método DEL (DeleteProduct):
Chamada:
http://localhost:8080/products/*Informe o Id do Produto que deseja apagar.*
Body none


Link do backup do banco de dados PostgreSql utilizado nos estudos, o mesmo foi criado atrabés do PGAdmin4:
https://drive.google.com/file/d/1lBfL-Bw4QYzKW1bC-u0DS7UDFTO8oRzs/view?usp=sharing
