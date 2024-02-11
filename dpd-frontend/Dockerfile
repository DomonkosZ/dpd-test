FROM node:latest

WORKDIR /dpd-frontend/

COPY package*.json ./

RUN npm install

# Copying typescript files
COPY public/ ./public
COPY src/ ./src
COPY tsconfig.json ./

# Transpiling typescript to javascript
RUN npm run build

CMD [ "npm", "start" ]