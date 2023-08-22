import { useNavigate } from "react-router-dom";

export function useNavigation() {
  const navigate = useNavigate();

  function goToHome() {
    navigate("/");
  }
  function goToDetail(id) {
    navigate(`/detail/${id}`);
  }
  function goToMyPage(id) {
    navigate(`/mypage`);
  }

  return {
    goToHome,
    goToDetail,
    goToMyPage,
  };
}
