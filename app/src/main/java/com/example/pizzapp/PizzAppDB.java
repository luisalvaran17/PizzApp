package com.example.pizzapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;

public class PizzAppDB extends SQLiteOpenHelper {
    public PizzAppDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static class DataBasePZ implements BaseColumns {
        public static final String TABLE_NAME_PIZZERIA = "pizzeria";
        public static final String COLUMN_ID_PIZERRIA = "id_pizzeria";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_TELEFONO = "telefono";
        public static final String COLUMN_DIRECCION = "direccion";

        public static final String TABLE_NAME_PROD = "producto";
        public static final String COLUMN_ID_PRODUCTO = "id_producto";
        public static final String COLUMN_NOMBRE_PROD = "nombre_prod";
        public static final String COLUMN_TIPO_PIZZA = "tipo_pizza";
        public static final String COLUMN_PRECIO_PROD = "precio_prod";
        public static final String COLUMN_DESCUENTO = "descuento";
        public static final String COLUMN_DISPONIBILIDAD = "disponibilidad";
        public static final String COLUMN_CANT_PORCIONES = "cant_porciones";
        public static final String COLUMN_ID_PIZZERIA_PRODUCTO = "id_pizzeria";

        public static final String TABLE_NAME_PEDIDO = "pedido";
        public static final String COLUMN_ID_PEDIDO = "id_pedido";
        public static final String COLUMN_FECHA_PEDIDO = "fecha_pedido";
        public static final String COLUMN_CANTIDAD_PRODUCTO = "cant_producto";
        public static final String COLUMN_TOTAL_PAGO = "total_pago";
        public static final String COLUMN_DIRECCION_PEDIDO = "direccion_pedido";
        public static final String COLUMN_TEL_CLIENTE_PEDIDO = "tel_cliente";
        public static final String COLUMN_ENTREGADO_CHECK = "entregado_check";
        public static final String COLUMN_ASIGNADO_CHECK = "asignado_check";
        public static final String COLUMN_ID_USUARIO_PEDIDO = "id_usuario";
        public static final String COLUMN_ID_PRODUCTO_PEDIDO = "id_producto";

        public static final String TABLE_NAME_USUARIOS = "usuarios";
        public static final String COLUMN_ID_USUARIOS = "id_usuarios";
        public static final String COLUMN_ID_NOMBRE = "nombre";
        public static final String COLUMN_CONTRASENA_USUARIO = "contrasena_usuario";
        public static final String COLUMN_CORREO_USUARIO = "correo_usuario";

        public static final String TABLE_NAME_REPARTIDOR = "repartidor";
        public static final String COLUMN_ID_REPARTIDOR = "id_repartidor";
        public static final String COLUMN_CANTIDAD_PEDIDO = "cantidad_pedido";

        public static final String TABLE_NAME_CLIENTE = "cliente";
        public static final String COLUMN_ID_CLIENTE = "id_cliente";
        public static final String COLUMN_DIRECCION_CLIENTE = "direccion_cliente";
        public static final String COLUMN_TEL_CLIENTE = "tel_cliente";

        public static final String TABLE_NAME_ASIGNACION = "asignacion";
        public static final String COLUMN_ID_REPARTIDOR_ASIG = "id_repartidor";
        public static final String COLUMN_ID_PEDIDO_ASIG = "id_pedido";
    }

    public static final String SQL_CREATE_PIZZERIA =

            "CREATE TABLE " + DataBasePZ.TABLE_NAME_PIZZERIA + " (" +
                    DataBasePZ.COLUMN_ID_PIZERRIA + " INTEGER PRIMARY KEY," +
                    DataBasePZ.COLUMN_NOMBRE + " TEXT," +
                    DataBasePZ.COLUMN_TELEFONO + " TEXT," +
                    DataBasePZ.COLUMN_DIRECCION + " TEXT)";

    public static final String SQL_CREATE_PRODUCTO =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_PROD + " (" +
                DataBasePZ.COLUMN_ID_PRODUCTO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataBasePZ.COLUMN_NOMBRE_PROD + " TEXT," +
                DataBasePZ.COLUMN_TIPO_PIZZA + " TEXT," +
                DataBasePZ.COLUMN_PRECIO_PROD + " INTEGER," +
                DataBasePZ.COLUMN_DESCUENTO + " INTEGER," +
                DataBasePZ.COLUMN_CANT_PORCIONES + " INTEGER," +
                DataBasePZ.COLUMN_ID_PIZZERIA_PRODUCTO + " INTEGER,"+
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_PIZZERIA_PRODUCTO + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_PIZZERIA + "(" + DataBasePZ.COLUMN_ID_PIZERRIA + ") )";

    public static final String SQL_CREATE_PEDIDO =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_PEDIDO + " (" +
                    DataBasePZ.COLUMN_ID_PEDIDO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DataBasePZ.COLUMN_FECHA_PEDIDO + " DATE," + // En la UI se representa como Date
                    DataBasePZ.COLUMN_CANTIDAD_PRODUCTO + " INTEGER," +
                    DataBasePZ.COLUMN_TOTAL_PAGO + " INTEGER," +
                    DataBasePZ.COLUMN_DIRECCION_PEDIDO + " TEXT," +
                    DataBasePZ.COLUMN_TEL_CLIENTE_PEDIDO + " TEXT," +
                    DataBasePZ.COLUMN_ENTREGADO_CHECK + " TEXT," +
                    DataBasePZ.COLUMN_ASIGNADO_CHECK + " TEXT," +
                    DataBasePZ.COLUMN_ID_USUARIO_PEDIDO + " INTEGER," +
                    DataBasePZ.COLUMN_ID_PRODUCTO_PEDIDO + " INTEGER," +
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_PRODUCTO_PEDIDO + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_PROD + "(" + DataBasePZ.COLUMN_ID_PRODUCTO + ")," +
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_USUARIO_PEDIDO + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_USUARIOS + "(" + DataBasePZ.COLUMN_ID_USUARIOS + ") )";


    public static final String SQL_CREATE_USUARIOS =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_USUARIOS + " (" +
                    DataBasePZ.COLUMN_ID_USUARIOS + " TEXT PRIMARY KEY," +
                    DataBasePZ.COLUMN_ID_NOMBRE +  " TEXT," +
                    DataBasePZ.COLUMN_CONTRASENA_USUARIO + " TEXT," +
                    DataBasePZ.COLUMN_CORREO_USUARIO + " TEXT)";

    public static final String SQL_CREATE_REPARTIDOR =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_REPARTIDOR + " (" +
                    DataBasePZ.COLUMN_ID_REPARTIDOR + " TEXT PRIMARY KEY," +
                    DataBasePZ.COLUMN_CANTIDAD_PEDIDO + " INTEGER,"+
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_REPARTIDOR + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_USUARIOS + "(" + DataBasePZ.COLUMN_ID_USUARIOS + ") )";


    public static final String SQL_CREATE_CLIENTE =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_CLIENTE + " (" +
                    DataBasePZ.COLUMN_ID_CLIENTE + " TEXT PRIMARY KEY," +
                    DataBasePZ.COLUMN_DIRECCION_CLIENTE + " TEXT," +
                    DataBasePZ.COLUMN_TEL_CLIENTE + " TEXT," +
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_CLIENTE + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_USUARIOS + "(" + DataBasePZ.COLUMN_ID_USUARIOS + ") )";

    public static final String SQL_CREATE_ASIGNACION =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_ASIGNACION + " (" +
                    DataBasePZ.COLUMN_ID_REPARTIDOR_ASIG + " TEXT," +
                    DataBasePZ.COLUMN_ID_PEDIDO_ASIG + " INTEGER," +
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_REPARTIDOR_ASIG + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_REPARTIDOR + "(" + DataBasePZ.COLUMN_ID_REPARTIDOR + ")," +
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_PEDIDO_ASIG + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_PEDIDO + "(" + DataBasePZ.COLUMN_ID_PEDIDO  + ") )";

    private static final String SQL_DELETE_PIZZERIA =
            "DROP TABLE IF EXISTS " + DataBasePZ.TABLE_NAME_PIZZERIA;

    private static final String SQL_DELETE_USUARIOS =
            "DROP TABLE IF EXISTS " + DataBasePZ.TABLE_NAME_USUARIOS;

    private static final String SQL_DELETE_CLIENTE =
            "DROP TABLE IF EXISTS " + DataBasePZ.TABLE_NAME_CLIENTE;

    private static final String SQL_DELETE_REPARTIDOR =
            "DROP TABLE IF EXISTS " + DataBasePZ.TABLE_NAME_REPARTIDOR;

    private static final String SQL_DELETE_PRODUCTO =
            "DROP TABLE IF EXISTS " + DataBasePZ.TABLE_NAME_PROD;

    private static final String SQL_DELETE_PEDIDO =
            "DROP TABLE IF EXISTS " + DataBasePZ.TABLE_NAME_PEDIDO;

    private static final String SQL_DELETE_ASIGNACION =
            "DROP TABLE IF EXISTS " + DataBasePZ.TABLE_NAME_ASIGNACION;

    // private final String SQL_JOIN_REPARTIDOR_PEDIDO = "SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.other_id WHERE b.property_id=?";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pizApp.db";

    public PizzAppDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // CONSULTAS PARA REPARTIDOR
    public boolean insertAsignacion(String id_repartidor, int id_pedido){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_repartidor", id_repartidor);
        contentValues.put("id_pedido", id_pedido);
        long result = db.insert("asignacion", null, contentValues);
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    // INSERTAR PEDIDOS
    public boolean insertPedido(Date fecha_pedido, int cantidad_producto,int total_pago,
                                String direccion_pedido,String tel_cliente, String entregado_check,
                                String asignado_check, int id_usuario,int id_producto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fecha_pedido", String.valueOf(fecha_pedido));
        contentValues.put("cant_producto", cantidad_producto);
        contentValues.put("total_pago", total_pago);
        contentValues.put("direccion_pedido", direccion_pedido);
        contentValues.put("tel_cliente", tel_cliente);
        contentValues.put("entregado_check", entregado_check);
        contentValues.put("asignado_check", asignado_check);
        contentValues.put("id_usuario", id_usuario);
        contentValues.put("id_producto", id_producto);
        long result = db.insert("pedido", null, contentValues);
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    // INSERT PRODUCTO
    public boolean insertProducto(int id_producto, String nombre_prod, String tipo_pizza,
                                  String precio_prod, String disponibilidad,
                                  String cant_porciones,String id_pizzeria){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_producto", id_producto);
        contentValues.put("nombre_prod", nombre_prod);
        contentValues.put("tipo_pizza", tipo_pizza);
        contentValues.put("precio_prod", precio_prod);
        contentValues.put("disponibilidad", disponibilidad);
        contentValues.put("cant_porciones", cant_porciones);
        contentValues.put("id_pizzeria", id_pizzeria);
        long result = db.insert("producto", null, contentValues);
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    // INSERT USUARIO
    public boolean insertUsuario(String id_usuario, String nombre, String contrasena, String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_usuarios", id_usuario);
        contentValues.put("nombre", nombre);
        contentValues.put("contrasena_usuario", contrasena);
        contentValues.put("correo_usuario", correo);
        long result = db.insert("usuarios", null, contentValues);
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    // INSRERT CLIENTE
    public boolean insertCliente(String id_cliente, String direccion_cliente, String tel_cliente){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_cliente", id_cliente);
        contentValues.put("direccion_cliente", direccion_cliente);
        contentValues.put("tel_cliente", tel_cliente);
        long result = db.insert("cliente", null, contentValues);
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    // INSERT REPARTIDOR
    public boolean insertRepartidor(String id_repartidor, int cantidad_pedido){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_repartidor", id_repartidor);
        contentValues.put("cantidad_pedido", cantidad_pedido);
        long result = db.insert("repartidor", null, contentValues);
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getPedidosNoAsignados(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[] {"false"};
        Cursor result = db.query(DataBasePZ.TABLE_NAME_PEDIDO, null , "asignado_check=?", args, null, null,null);
        // System.out.println("result: " + result.toString());
        return result;
    }

    //CONSULTA PEDIDOS
    public Cursor getPedidos(String id_uduario){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[] {id_uduario};
        Cursor result = db.query(DataBasePZ.TABLE_NAME_PEDIDO, null , "id_usuario=?", args, null, null,null);
        return result;
    }

    public Cursor getPedidosAsignados(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[] {"false", "true"};
        Cursor result = db.rawQuery("SELECT * FROM " + DataBasePZ.TABLE_NAME_PEDIDO + " WHERE " +
                DataBasePZ.COLUMN_ENTREGADO_CHECK+"=?" + " AND " + DataBasePZ.COLUMN_ASIGNADO_CHECK + "=?", args);
        // System.out.println("result: " + result.toString());
        return result;
    }

    public Cursor getPedidosEntregados(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[] {"true", "true"};
        Cursor result = db.rawQuery("SELECT * FROM " + DataBasePZ.TABLE_NAME_PEDIDO + " WHERE " +
                DataBasePZ.COLUMN_ENTREGADO_CHECK+"=?" + " AND " + DataBasePZ.COLUMN_ASIGNADO_CHECK + "=?", args);
        // System.out.println("result: " + result.toString());
        return result;
    }

    public Cursor getPedido(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[] {id};
        Cursor result = db.query(DataBasePZ.TABLE_NAME_PEDIDO, null , "id_pedido=?", args, null, null,null);
        // System.out.println("result: " + result.toString());
        return result;
    }

    public boolean updatePedidoAsignado(int id_pedido){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("asignado_check", "true");
        String[] args = new String[] {String.valueOf(id_pedido)};
        long result = db.update(DataBasePZ.TABLE_NAME_PEDIDO, cv , "id_pedido=?", args);
        // System.out.println("result: " + result.toString());
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    public int cantidadPedidos(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[] {"1"}; // OBTENER ID REPARTIDOR DESDE LOGIN
        int cantidad = 0;
        Cursor result = db.query(DataBasePZ.TABLE_NAME_REPARTIDOR, null , "id_repartidor=?", args,null,null,null);
        //Cursor result = db.rawQuery("SELECT cantidad_pedido FROM " + DataBasePZ.TABLE_NAME_REPARTIDOR, args);
        // System.out.println("result: " + result.toString());

        try {

            if (result.moveToFirst()) {
                do {
                    cantidad = result.getInt(1);

                } while (result.moveToNext());
            }
            cantidad = cantidad + 1;
            //System.out.println("cantidad: " + cantidad);
            return cantidad;
        }catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return cantidad;
    }

    public boolean updateCantidadPedidosEntregados(int repartidor_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int cantidad_pedido = cantidadPedidos();

        cv.put("cantidad_pedido", String.valueOf(cantidad_pedido));
        System.out.println(cantidad_pedido);
        String[] args = new String[] {"1"};  // Colocar id repartidor
        long result = db.update(DataBasePZ.TABLE_NAME_REPARTIDOR, cv, "id_repartidor=?", args);
        // System.out.println("result: " + result.toString());
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updatePedidoEntregado(int id_pedido){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("entregado_check", "true");
        String[] args = new String[] {String.valueOf(id_pedido)};
        long result = db.update(DataBasePZ.TABLE_NAME_PEDIDO, cv , "id_pedido=?", args);
        // System.out.println("result: " + result.toString());
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PIZZERIA);
        db.execSQL(SQL_CREATE_PRODUCTO);
        db.execSQL(SQL_CREATE_PEDIDO);
        db.execSQL(SQL_CREATE_USUARIOS);
        db.execSQL(SQL_CREATE_REPARTIDOR);
        db.execSQL(SQL_CREATE_CLIENTE);
        db.execSQL(SQL_CREATE_ASIGNACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PIZZERIA);
        db.execSQL(SQL_DELETE_PRODUCTO);
        db.execSQL(SQL_DELETE_PEDIDO);
        db.execSQL(SQL_DELETE_USUARIOS);
        db.execSQL(SQL_DELETE_REPARTIDOR);
        db.execSQL(SQL_DELETE_CLIENTE);
        db.execSQL(SQL_DELETE_ASIGNACION);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
