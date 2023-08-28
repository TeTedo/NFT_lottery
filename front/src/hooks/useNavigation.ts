import { useNavigate } from "react-router-dom";

export function useNavigation() {
  const navigate = useNavigate();

  function goToHome() {
    navigate("/");
  }
  function goToDetail(id: string) {
    navigate(`/detail/${id}`);
  }
  function goToMyPage() {
    navigate(`/mypage`);
  }

  return {
    goToHome,
    goToDetail,
    goToMyPage,
  };
}
