FROM node:16-alpine 

WORKDIR /usr/src/app

COPY --chown=node:node . /usr/src/app

RUN npm install -g @angular/cli@14.2.13

RUN npm install



CMD ["ng", "serve", "--host", "0.0.0.0"]