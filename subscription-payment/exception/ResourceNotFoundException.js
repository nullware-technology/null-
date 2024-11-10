class ResourceNotFoundException extends Error {
  constructor(message) {
    super(message);
    this.name = "ResourceNotFoundException";
    this.status = 404;
  }
}

module.exports = ResourceNotFoundException;