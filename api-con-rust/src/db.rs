use sqlx::MySqlPool;
use dotenv::dotenv;
use std::env;

pub async fn get_connection_pool() -> MySqlPool {
    dotenv().ok();
    let database_url = env::var("DATABASE_URL").expect("DATABASE_URL no encontrada en .env");
    
    MySqlPool::connect(&database_url)
        .await
        .expect("Error al conectar con la base de datos")
}
