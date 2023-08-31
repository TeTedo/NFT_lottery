import styled from "styled-components";

const Contents = {
  Container: styled.div`
    position: fixed;
    box-sizing: border-box;
    width: 100%;
    height: 60px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    background-color: rgba(0, 0, 0, 0.1);
    align-content: center;
    justify-content: center;
    flex-wrap: nowrap;
    color: #fff;
    border-bottom: 1px solid #fff;
  `,
  Content: styled.div`
    width: 1200px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  `,
  Logo: styled.div`
    font-size: 25px;
    font-weight: bold;
    color: #fff;
    cursor: pointer;
  `,
  TitleContainer: styled.div`
    display: flex;
    gap: 100px;
  `,

  Title: styled.div`
    font-size: 20px;
    cursor: pointer;
  `,
  Login: styled.div`
    border: 1px solid #fff;
    padding: 5px 10px 5px 10px;
    border-radius: 15px;
    cursor: pointer;
  `,
  NickName: styled.div`
    width: 350px;
    overflow: hidden; // 을 사용해 영역을 감출 것
    text-overflow: ellipsis; // 로 ... 을 만들기
    white-space: nowrap; // 아래줄로 내려가는 것을 막기위해
    word-break: break-all;
    height: 20px;
    cursor: pointer;
  `,
};

export { Contents };
