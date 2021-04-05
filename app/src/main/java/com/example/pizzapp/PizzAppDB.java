package com.example.pizzapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
        public static final String COLUMN_ID_PEDIDO_PRODUCTO = "id_pedido";
        public static final String COLUMN_ID_PIZZERIA_PRODUCTO = "id_pizzeria";

        public static final String TABLE_NAME_PEDIDO = "pedido";
        public static final String COLUMN_ID_PEDIDO = "id_pedido";
        public static final String COLUMN_FECHA_PEDIDO = "fecha_pedido";
        public static final String COLUMN_CANTIDAD_PRODUCTO = "cant_producto";
        public static final String COLUMN_TOTAL_PAGO = "total_pago";
        public static final String COLUMN_DIRECCION_PEDIDO = "direccion_pedido";
        public static final String COLUMN_ENTREGADO_CHECK = "entregado_check";
        public static final String COLUMN_ID_USUARIO_PEDIDO = "id_usuario";

        public static final String TABLE_NAME_USUARIOS = "usuarios";
        public static final String COLUMN_ID_USUARIOS = "id_usuarios";
        public static final String COLUMN_NOMBRE_USUARIO = "nombre_usuario";  // QUITAR
        public static final String COLUMN_CONTRASENA_USUARIO = "contrasena_usuario";
        public static final String COLUMN_CORREO_USUARIO = "correo_usuario";

        public static final String TABLE_NAME_REPARTIDOR = "repartidor";
        public static final String COLUMN_ID_REPARTIDOR = "id_repartidor";
        public static final String COLUMN_CANTIDAD_PEDIDO = "cantidad_pedido";

        public static final String TABLE_NAME_CLIENTE = "cliente";
        public static final String COLUMN_ID_CLIENTE = "id_cliente";
        public static final String COLUMN_DIRECCION_CLIENTE = "direccion_cliente";
        public static final String COLUMN_FECHA_NAC = "fecha_nac";
        public static final String COLUMN_EDAD = "edad";

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
                DataBasePZ.COLUMN_DISPONIBILIDAD + " INTEGER," +
                DataBasePZ.COLUMN_CANT_PORCIONES + " INTEGER," +
                DataBasePZ.COLUMN_ID_PEDIDO_PRODUCTO + " INTEGER," +
                DataBasePZ.COLUMN_ID_PIZZERIA_PRODUCTO + " INTEGER,"+
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_PEDIDO_PRODUCTO + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_PEDIDO + "(" + DataBasePZ.COLUMN_ID_PEDIDO + ")," +
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_PIZZERIA_PRODUCTO + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_PIZZERIA + "(" + DataBasePZ.COLUMN_ID_PIZERRIA + ") )";

    public static final String SQL_CREATE_PEDIDO =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_PEDIDO + " (" +
                    DataBasePZ.COLUMN_ID_PEDIDO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DataBasePZ.COLUMN_FECHA_PEDIDO + " DATE," + // En la UI se representa como Date
                    DataBasePZ.COLUMN_CANTIDAD_PRODUCTO + " INTEGER," +
                    DataBasePZ.COLUMN_TOTAL_PAGO + " INTEGER," +
                    DataBasePZ.COLUMN_DIRECCION_PEDIDO + " TEXT," +
                    DataBasePZ.COLUMN_ENTREGADO_CHECK + " TEXT," +
                    DataBasePZ.COLUMN_ID_USUARIO_PEDIDO + " INTEGER," +
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_USUARIO_PEDIDO + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_USUARIOS + "(" + DataBasePZ.COLUMN_ID_USUARIOS + ") )";

    public static final String SQL_CREATE_USUARIOS =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_USUARIOS + " (" +
                    DataBasePZ.COLUMN_ID_USUARIOS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DataBasePZ.COLUMN_NOMBRE_USUARIO + " TEXT," +
                    DataBasePZ.COLUMN_CONTRASENA_USUARIO + " TEXT," +
                    DataBasePZ.COLUMN_CORREO_USUARIO + " TEXT)";

    public static final String SQL_CREATE_REPARTIDOR =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_REPARTIDOR + " (" +
                    DataBasePZ.COLUMN_ID_REPARTIDOR + " INTEGER PRIMARY KEY," +
                    DataBasePZ.COLUMN_CANTIDAD_PEDIDO + " INTEGER,"+
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_REPARTIDOR + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_USUARIOS + "(" + DataBasePZ.COLUMN_ID_USUARIOS + ") )";


    public static final String SQL_CREATE_CLIENTE =
            "CREATE TABLE " + DataBasePZ.TABLE_NAME_CLIENTE + " (" +
                    DataBasePZ.COLUMN_ID_CLIENTE + " INTEGER PRIMARY KEY," +
                    DataBasePZ.COLUMN_DIRECCION_CLIENTE + " TEXT," +
                    DataBasePZ.COLUMN_FECHA_NAC + " DATE," +
                    DataBasePZ.COLUMN_EDAD + " INTEGER," +
                    "FOREIGN KEY(" + DataBasePZ.COLUMN_ID_CLIENTE + ") REFERENCES " +
                    DataBasePZ.TABLE_NAME_USUARIOS + "(" + DataBasePZ.COLUMN_ID_USUARIOS + ") )";


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

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pizApp.db";

    public PizzAppDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public boolean insertUsuario(String nombre_usuario, String contrasena, String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre_usuario", nombre_usuario);
        contentValues.put("contrasena_usuario", contrasena);
        contentValues.put("correo_usuario", correo);
        long result = db.insert("usuarios", null, contentValues);
        if (result == 1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getPedidos(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[] {"false"};
        Cursor result = db.query(DataBasePZ.TABLE_NAME_PEDIDO, null , "entregado_check=?", args, null, null,null);
        System.out.println("result: " + result.toString());
        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PIZZERIA);
        db.execSQL(SQL_CREATE_PRODUCTO);
        db.execSQL(SQL_CREATE_PEDIDO);
        db.execSQL(SQL_CREATE_USUARIOS);
        db.execSQL(SQL_CREATE_REPARTIDOR);
        db.execSQL(SQL_CREATE_CLIENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PIZZERIA);
        db.execSQL(SQL_DELETE_PRODUCTO);
        db.execSQL(SQL_DELETE_PEDIDO);
        db.execSQL(SQL_DELETE_USUARIOS);
        db.execSQL(SQL_DELETE_REPARTIDOR);
        db.execSQL(SQL_DELETE_CLIENTE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
