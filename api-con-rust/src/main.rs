/*use actix_web::{HttpServer, App};

#[actix_web::main]
async fn main() -> std::io::Result<()>{
    HttpServer::new(|| App::new())
    .bind(("127.0.0.1", 8080))?
    .run()
    .await
}*/

use actix_web::{App, HttpServer, web};
//use sqlx::MySqlPool;
mod db;
mod insertar;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let pool = db::get_connection_pool().await;

    HttpServer::new(move || {
        App::new()
            .app_data(web::Data::new(pool.clone()))
            .service(insertar::insertar_usuario)
    })
    .bind("127.0.0.1:8080")?
    .run()
    .await
}
