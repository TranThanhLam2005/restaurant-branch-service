// package com.example.branch.config;

// import java.util.Map;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.stereotype.Component;

// import com.example.branch.entity.Branch;

// // Redis helper disabled - using basic database operations only
// //@Component
// public class BranchRedisHelper {
      
//     @Autowired
//     private RedisTemplate<String, Branch> redisTemplate; 
//     public void createRedisString(String key, Branch value) {
//         redisTemplate.opsForValue()
//             .set(key, value);
//     }

//     public void createHash (
//         String redisKey, 
//         String hashKey,
//         Branch hashValue
//     ) {
//         redisTemplate.opsForHash()
//             .putIfAbsent(redisKey, hashKey, hashValue);
//     }

//     public void createSortedSet (
//         String redisKey, 
//         Branch entry,
//         double score
//     ) {
//         redisTemplate.opsForZSet()
//             .addIfAbsent(redisKey, entry, score);
//     }

//     public Branch getStrValueByKey(String key) {
//         return redisTemplate
//             .opsForValue()
//             .get(key);
//     }

//     public Map<Object, Object> getHashByKey(String key) {
//         return redisTemplate.opsForHash()
//             .entries(key);
//     }

//     public Branch getHashValueByKeys(
//             String redisKey, String hashKey
//     ) {
//         return (Branch) redisTemplate
//             .opsForHash()
//             .get(redisKey, hashKey);
//     }

//     public Set<Branch> getElementsByScoreRange(
//         String redisKey,
//         double min,
//         double max
//     ) {
//         return redisTemplate.opsForZSet()
//             .rangeByScore(redisKey, min, max);
//     }

//     // Store all branches in a hash
//     public void storeAllBranches(String hashKey, Map<String, Branch> branches) {
//         if (branches != null && !branches.isEmpty()) {
//             redisTemplate.opsForHash().putAll(hashKey, branches);
//         }
//     }

//     // Get all branches from hash
//     public Map<Object, Object> getAllBranchesFromHash(String hashKey) {
//         return redisTemplate.opsForHash().entries(hashKey);
//     }

//     // Check if hash key exists
//     public Boolean hasKey(String key) {
//         return redisTemplate.hasKey(key);
//     }

//     // Delete a key
//     public Boolean deleteKey(String key) {
//         return redisTemplate.delete(key);
//     }
// }
