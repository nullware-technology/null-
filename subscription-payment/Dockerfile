# Use the official Node.js image as the base
FROM node:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy only package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install production dependencies
RUN npm install --production && npm cache clean --force

# Copy the rest of the application code
COPY . .

# Expose the port the Express app runs on
EXPOSE 8083

# Default command to start the application
CMD ["npm", "start"]
