import express from 'express';
import dotenv from 'dotenv';

dotenv.config();

const app = express();
const port = process.env.PORT;

app.disable('x-powered-by');

app.get('/hit', (req, res) => {
  console.log(req, res);
  res.status(200).send();
});

app.listen(port, () => {
  console.log(`http server listening on port ${port}`);
});
