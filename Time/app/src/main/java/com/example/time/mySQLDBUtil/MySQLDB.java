package com.example.time.mySQLDBUtil;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import com.example.time.dataBase.MainActivityDataBase;
import com.example.time.modifySchedule.BitmapToByte;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public enum MySQLDB {
    INSTANCE;
    public void upLoadData(Context context){
        new Thread(() -> {
            try {
                //建立云数据库链接
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://rm-bp183f2u68r6s15y30o.mysql.rds.aliyuncs.com:3306/mysqldatabase";
                String username = "mysqlwyj";
                String password = "admin123456";
                Connection connection = DriverManager.getConnection(url,username,password);

                //删除云端数据库并重新建立一个空表
                String sql = "DROP TABLE IF EXISTS test;\n" +
                        "CREATE TABLE `test` (\n" +
                        "  `name` varchar(20) NOT NULL,\n" +
                        "  `type` varchar(20) DEFAULT NULL,\n" +
                        "  `note` varchar(20) DEFAULT NULL,\n" +
                        "  `level` varchar(20) DEFAULT NULL,\n" +
                        "  `datetime` datetime DEFAULT NULL,\n" +
                        "  `alarmClock` datetime DEFAULT NULL,\n" +
                        "  `alarmClockOpen` int(11) DEFAULT NULL,\n" +
                        "  `location` varchar(40) DEFAULT NULL,\n" +
                        "  `locationOpen` int(11) DEFAULT NULL,\n" +
                        "  `photo` blob,\n" +
                        "  PRIMARY KEY (`name`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                        "SELECT * FROM mysqldatabase.test;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeQuery();
                //查询本地的数据库
                ContentResolver resolver = context.getContentResolver();
                Uri uri = Uri.parse("content://com.example.time.contentProvider.scheduleProvider/schedules");
                @SuppressLint("Recycle") Cursor cursor = resolver.query(uri,null,null,null,null);
                assert cursor != null;
                PreparedStatement preparedStatement2;
                String sql2 = "insert into `test` (name,type,note,level,datetime,alarmClock,alarmClockOpen,location,locationOpen,photo) values(?,?,?,?,?,?,?,?,?,?)";
                preparedStatement2 = connection.prepareStatement(sql2);
                while(cursor.moveToNext()){
                    String name = cursor.getString(0);
                    String type = cursor.getString(1);
                    String level = cursor.getString(2);
                    String message = cursor.getString(3);
                    String datetime = cursor.getString(4);
                    String alarmClock = cursor.getString(5);
                    int alarmClockOpen = cursor.getInt(6);
                    String location = cursor.getString(7);
                    int locationOpen = cursor.getInt(8);
                    byte[] photo = cursor.getBlob(9);
                    preparedStatement2.setString(1,name);
                    preparedStatement2.setString(2,type);
                    preparedStatement2.setString(3,message);
                    preparedStatement2.setString(4,level);
                    preparedStatement2.setString(5,datetime);
                    preparedStatement2.setString(6,alarmClock);
                    preparedStatement2.setInt(7,alarmClockOpen);
                    preparedStatement2.setString(8,location);
                    preparedStatement2.setInt(9,locationOpen);
                    //照片转换
                    Bitmap bmpout = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    byte[] bytesPhoto = BitmapToByte.saveBitmap(bmpout);
                    preparedStatement2.setBytes(10,bytesPhoto);
                    preparedStatement2.executeQuery();
                }
                preparedStatement2.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void downLoadData(Context context){
        new Thread(() -> {
            try {
                //建立数据库链接
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://rm-bp183f2u68r6s15y30o.mysql.rds.aliyuncs.com:3306/mysqldatabase";
                String username = "mysqlwyj";
                String password = "admin123456";
                Connection connection = DriverManager.getConnection(url,username,password);

                String sql = "SELECT \n" +
                        "    name,\n" +
                        "    type,\n" +
                        "    note,\n" +
                        "    level,\n" +
                        "    datetime,\n" +
                        "    alarmClock,\n" +
                        "    alarmClockOpen,\n" +
                        "    location,\n" +
                        "    locationOpen,\n" +
                        "    photo\n" +
                        "FROM\n" +
                        "    mysqldatabase.test\n" +
                        "ORDER BY datetime";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                MainActivityDataBase mainActivityDatabase;
                mainActivityDatabase = new MainActivityDataBase(context);
                SQLiteDatabase db = mainActivityDatabase.getWritableDatabase();
                db.execSQL("DROP TABLE IF EXISTS `schedules`;");
                db.execSQL("CREATE TABLE schedules (\n" +
                        "    name VARCHAR(20),\n" +
                        "    type VARCHAR(20),\n" +
                        "    level VARCHAR(20),\n" +
                        "    message VARCHAR(20),\n" +
                        "    datetime DATETIME,\n"+
                        "    alarmClock DATETIME,\n"+
                        "    alarmClockOpen DATETIME,\n"+
                        "    location VARCHAR(20),\n"+
                        "    locationOpen VARCHAR(20),\n"+
                        "    photo blob)");
                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");
                    String message = resultSet.getString("note");
                    String level = resultSet.getString("level");
                    String datetime = resultSet.getString("datetime").substring(0,19);
                    String alarmClock = resultSet.getString("alarmClock");
                    int alarmClockOpen = resultSet.getInt("alarmClockOpen");
                    String location = resultSet.getString("location");
                    int locationOpen = resultSet.getInt("locationOpen");
                    Blob photo = resultSet.getBlob("photo");
                    db.execSQL("INSERT INTO `schedules` (`name`, `type`, `message`, `level`, `datetime`, `alarmClock`,`alarmClockOpen`,`location`,`locationOpen`,`photo`) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",new Object[]{name,type,message,level,datetime,alarmClock,alarmClockOpen,location,locationOpen,photo});
                    Log.i("TESTDATABASE",name);
                    Log.i("TESTDATABASE",type);
                    Log.i("TESTDATABASE",message);
                    Log.i("TESTDATABASE",level);
                    Log.i("TESTDATABASE",datetime);
                    Log.i("TESTDATABASE",alarmClock);
                    Log.i("TESTDATABASE",alarmClockOpen+"");
                    Log.i("TESTDATABASE",location);
                    Log.i("TESTDATABASE",locationOpen+"");
                    Log.i("TESTDATABASE",String.valueOf(photo));
                }
                preparedStatement.close();
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
}