# 캡스톤 디자인 팀 프로젝트

## Easy Drink

### 개요
사용자가 입력한 정보와 어플리케이션 사용 과정에서 축적된 데이터를 바탕으로 프랜차이즈 카페에서 판매 중인 음료를 추천하고, 각 음료 별 영양성분 및 재료, 타 이용자들이 남긴 후기를 통해 사용자의 음료 선택에 도움을 주어 보다 합리적인 소비를 돕고자 한다.


### 기능

1. 음료 추천
  - 입력한 취향 정보에 따른 추천
  - 인기 음료, 평가하지 않은 음료 중 추천
  - 설정에 따라 제외 가능

2. 정보 제공
  - 음료를 제공하는 프랜차이즈와 가격
  - 음료 별 영양성분 및 포함된 재료
  - 음료 별 후기

### 구현

1. 추천 기능
- Ward와 K-MEANS 알고리즘 등 통계적 방법을 활용한 군집화
- 코사인거리를 이용한 유사 음료 추천 기능 구현
- 협업 필터링 기법을 활용한 추천 기능 구현

2. DB
 - 서버 부담을 줄이기 위한 데이터베이스 이원화

![image](https://user-images.githubusercontent.com/95491950/147234473-1bbaf08a-3189-46d2-96cb-f30847c44b25.png)

### 시연
![image](https://user-images.githubusercontent.com/95491950/147234687-3bf56d41-8086-4b97-a9db-fe313300a1e2.png)

### 기대 효과

1. 하나의 플랫폼에서 자신이 좋아하는 음료와 다양한 음료를 비교, 확인 가능함
2. 조건에 따른 음료 탐색이 가능하여 최적의 구매 선택지를 제공하고 고민 시간을 줄일 수 있음
3. 미지의 음료에 대한 막연한 부담감 감소하고 음료 별 감상을 타인과 공유할 수 있음



