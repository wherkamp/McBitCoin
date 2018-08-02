package me.kingtux.minecoin.storage;

public enum SqlQueries {
  CREATE_TABLE(
      "CREATE TABLE IF NOT EXISTS `users` ( `id` INT NOT NULL AUTO_INCREMENT,`uuid` TEXT NOT NULL, `balance` INT NOT NULL, PRIMARY KEY (`id`) )"),

  getUser("SELECT * FROM `users` WHERE uuid=? LIMIT 1"),
  CREATE_USER("INSERT INTO `users` (uuid, balance) VALUES (?,?)"),
  UPDATE_BALANCE("UPDATE users SET balance=? WERE uuid=?");

  private String query;

  SqlQueries(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }
}
