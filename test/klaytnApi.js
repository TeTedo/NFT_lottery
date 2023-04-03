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
const config = {
  baseURL: 'https://th-api.klaytnapi.com/v2/',
  headers: {
    'Content-Type': 'application/json',
    'x-chain-id': CHAIN_ID.main,
    Authorization: CREDENTIAL,
  },
};

app.use(cors({ origin: '*' }));

app.get('/queryTokens/:account', async (req, res) => {
  const { account } = req.params;

  try {
    const response = await axios.get(`account/${account}/token`, {
      ...config,
      params: { kind: 'nft' },
    });
    const items = response.data.items;
    const data = new Array();

    for (const item of items) {
      let metaData = null;

      try {
        if (item.extras.tokenUri) {
          const metaDataResponse = await axios.get(item.extras.tokenUri, { timeout: 500 });
          metaData = metaDataResponse.data;
        }
      } catch (err) {
        console.log(`Failed to fetch metadata for token ${item.contractAddress}: ${err.message}`);
      }

      data.push({ ...item, metaData });
    }

    res.send({ success: true, data });
  } catch (err) {
    console.log(`Failed to fetch tokens for account ${account}: ${err.message}`);
    res.send({ success: false });
  }
});

app.listen(PORT, () => {
  console.log(PORT + '에 서버 열림');
});
