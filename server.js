const express=require("express");
const sql=require("mssql");
const cors=require("cors");
const app=express();
const port=3000;

app.use(cors());
app.use(express.json());

//Conexión bas de datos

const dbConfig={
    user:"root",
    password:"root",
    server:"172.30.30.35",
    database:"BIBLIOTECA",
    options:{
        encrypt:true,
        trustSertificate:true,
    }
};

//Código para obtener datos de la tabla Libro

app.get("/get-data",async (req,res)=>{
    try{
        const pool=await sql.commect(dbConfig);
        const result=await pool.request().query("SELECT * FROM LIBRO");
        res.json(Result.recordset);
    }catch(error){
        console.error("Error al conectar la base de datos: ", error);
        res.status(500).send("Error al conectar la base de datos");
    }
});

//Código para insertar datos a la base de datos
app.post("/add-book",async(req,res)=>{
    const{Titulo, Autor, Fecha, ISBN}=req.body;
    try{
        const pool=await sql.connect(dbConfig);
        await pool
        .request()
        .input("Titulo", sql.Varchar, Titulo)
        .input("Autor", sql.Varchar, Autor)
        .input("Fecha", sql.Varchar, Fecha)
        .input("ISBN", sql.Varchar, ISBN)
        .query(
            "INSERT INTO LIBRO (Titulo,Autor,Fecha,ISBN) VALUES (@Titulo,@Autor,@Fecha,@ISBN)");
            res.send("Libro agregado exitosamente");
    }catch (error){
        console.error("Error al conectar la base de datos: ", error);
        res.sendStatus(500).send("Error al insertar los datos");
    }
});

//Iniciar el servidor
app.listen(port, ()=>{
    console.log("Servidor corriendo en http://localhost:${port}");
});