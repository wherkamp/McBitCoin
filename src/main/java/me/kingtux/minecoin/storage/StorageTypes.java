package me.kingtux.minecoin.storage;

public enum StorageTypes {

  Mysqli("mysqli", new MysqlStorage()),
  yaml("yaml", new YamlStorage());
  private String name;
  private Storage storage;

  StorageTypes(String name, Storage storage) {
    this.name = name;
    this.storage = storage;
  }

  public static StorageTypes getStorageType(String name) {
    for (StorageTypes storageType : values()) {
      if (storageType.name.equalsIgnoreCase(name)) {
        return storageType;
      }
    }

    return null;
  }

  public Storage getStorage() {
    return storage;
  }
}
