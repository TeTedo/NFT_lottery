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
    url: `https://th-api.klaytnapi.com/v2/account/${account}/contract`,
    params: { kind: 'nft' },
    headers: {
      'Content-Type': 'application/json',
      'x-chain-id': CHAIN_ID.main,
      Authorization: CREDENTIAL,
    },
  };

  try {
    const response = await axios.request(options);
    res.send({ success: true, data: response.data });
  } catch (err) {
    console.log(err);
    res.send({ success: false });
  }
});

app.listen(PORT, () => {
  console.log(PORT + '에 서버 열림');
});
