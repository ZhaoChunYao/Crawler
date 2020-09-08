package zhaochunyao.crawler.serviceImpl;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import zhaochunyao.crawler.entity.Page;
import zhaochunyao.crawler.service.StoreService;

public class MySQLStoreService implements StoreService {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/fangtianxia?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "200901";
 

	public void store(Page page) {
		// TODO Auto-generated method stub
		Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
            // 执行插入
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "insert into comments values('" +page.getCity()+"',"+page.getHouseId()+",'"
            		+page.getHouseName()+"','"+page.getPageUrl()+"','"+page.getCommentPageUrl()+"','"
            		+page.getAllComments()+"','"+"2020-7-30"+"');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        
            // 完成后关闭
            stmt.close();
            conn.close();
        }catch(SQLIntegrityConstraintViolationException e){
        	//试图插入已存在的数据，所以失败，不用管它
        	//e.printStackTrace();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
	}

}
