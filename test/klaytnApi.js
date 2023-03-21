const express = require('express');
const app = express();
const axios = require('axios').default;
const cors = require('cors');
const PORT = 4000;

const CHAIN_ID = {
  main: '8217',
  test: '1001',
};
const CREDENTIAL =
  'Basic S0FTS0xZV1lLQUI3TUNRQTc2SU5JRVZBOkpUeVpHaGoxclZ6R0lsM1dtT3oxb0V1YlpoZmtCUDNQUXBIQnVmNm0=';

app.use(cors({ origin: '*' }));

app.get('/queryTokens/:account', async (req, res) => {
  const { account } = req.params;

  const options = {
    method: 'GET',
    url: `https://th-api.klaytnapi.com/v2/account/${account}/token`,
    params: { kind: 'nft' },
    headers: {
      'Content-Type': 'application/json',
      'x-chain-id': CHAIN_ID.main,
      Authorization: CREDENTIAL,
    },
  };

  try {
    const response = await axios.request(options);
    const items = response.data.items;
    const data = new Array();

    for (const item of items) {
      let metaData;

      try {
        metaData = item.extras.tokenUri
          ? (await axios.get(item.extras.tokenUri, { timeout: 500 })).data
          : null;
      } catch (err) {
        metaData = null;
      }

      item.metaData = metaData;
      data.push(item);
    }
    res.send({ success: true, data });
  } catch (err) {
    console.log(err);
    res.send({ success: false });
  }
});

app.listen(PORT, () => {
  console.log(PORT + '에 서버 열림');
});
